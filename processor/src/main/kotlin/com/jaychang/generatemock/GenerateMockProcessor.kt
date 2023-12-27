package com.jaychang.generatemock

import com.google.devtools.ksp.isAbstract
import com.google.devtools.ksp.processing.*
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.google.devtools.ksp.symbol.KSVisitorVoid
import com.google.devtools.ksp.validate
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.ksp.toClassName
import com.squareup.kotlinpoet.ksp.toTypeName
import com.squareup.kotlinpoet.ksp.writeTo
import java.io.File
import java.io.OutputStream

class GenerateMockProcessor(
    private val logger: KSPLogger,
    private val codeGenerator: CodeGenerator
) : SymbolProcessor {
    override fun process(resolver: Resolver): List<KSAnnotated> {
        val generateMockClasses = resolver
            .getSymbolsWithAnnotation(GenerateMock::class.java.name)
            .filterIsInstance<KSClassDeclaration>()

        if (!generateMockClasses.iterator().hasNext()) return emptyList()

        for (generateMockClass in generateMockClasses) {
            val packageName = generateMockClass.packageName.asString()
            val fileName = "${generateMockClass.simpleName.getShortName()}_Generated"
            val dependencies = Dependencies(false, generateMockClass.containingFile!!)
            val visitor = Visitor(
                dependencies = dependencies,
                className = ClassName(packageName, fileName)
            )
            generateMockClass.accept(visitor, Unit)
        }

        return generateMockClasses.filterNot { it.validate() }.toList()
    }

    inner class Visitor(
        private val dependencies: Dependencies,
        private val className: ClassName,
    ) : KSVisitorVoid() {
        override fun visitClassDeclaration(classDeclaration: KSClassDeclaration, data: Unit) {
            if (!classDeclaration.isAbstract()) {
                logger.error("The annotated class ${classDeclaration.simpleName.asString()} must be abstract class.")
                return
            }

            val functionsNeedToImpl = classDeclaration.getAllFunctions().filter { it.isAbstract }

            if (!functionsNeedToImpl.iterator().hasNext()) {
                logger.info("The annotated class ${classDeclaration.qualifiedName?.asString()} has no functions to be mocked.")
                return
            }

            val classTypeSpec = TypeSpec.classBuilder(className.simpleName)
                .superclass(classDeclaration.asType(emptyList()).toTypeName())
                .addFunctions(
                    functionsNeedToImpl.map { it.toFunSpec() }.toList()
                )
                .build()
            val factoryClassTypeSpec = TypeSpec.objectBuilder("${classDeclaration.simpleName.asString()}Factory")
                .addFunction(
                    FunSpec.builder("create")
                        .returns(classDeclaration.toClassName())
                        .addStatement("return %T()", className)
                        .build()
                )
                .build()
            val fileSpec = FileSpec.builder(className)
                .addType(classTypeSpec)
                .addType(factoryClassTypeSpec)
                .build()
            fileSpec.writeTo(codeGenerator, dependencies)
        }
    }
}