package com.jaychang.generatemock

import com.google.devtools.ksp.isAbstract
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.google.devtools.ksp.symbol.KSPropertyDeclaration
import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ksp.toKModifier
import com.squareup.kotlinpoet.ksp.toTypeName
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.asTypeName

internal fun KSFunctionDeclaration.toFunSpec(): FunSpec {
    val functionName = simpleName.asString()
    val funSpecBuilder = FunSpec.builder(functionName)

    if (isAbstract) {
        funSpecBuilder.addModifiers(KModifier.OVERRIDE)
    }

    val isSuspendFunction = modifiers.any { it.toKModifier() == KModifier.SUSPEND }
    if (isSuspendFunction) {
        funSpecBuilder.addModifiers(KModifier.SUSPEND)
    }

    for (parameter in parameters) {
        val parameterName = parameter.name!!.asString()
        val parameterType = parameter.type.resolve().toTypeName()
        val parameterSpec = ParameterSpec.builder(parameterName, parameterType).build()
        funSpecBuilder.addParameter(parameterSpec)
    }

    val notMockedException = ClassName(NotMockedException::class.java.`package`.name, NotMockedException::class.simpleName!!)
    funSpecBuilder.addStatement("throw %T(%S)", notMockedException, "The function $functionName is not mocked yet.")

    val returnType = returnType?.resolve()?.toTypeName() ?: Unit::class.asTypeName()
    funSpecBuilder.returns(returnType)

    return funSpecBuilder.build()
}

internal fun KSPropertyDeclaration.toPropertySpec(): PropertySpec {
    val propertyName = simpleName.asString()
    val propertyType = type.toTypeName()

    val propertySpecBuilder = PropertySpec.builder(propertyName, propertyType)

    if (isAbstract()) {
        propertySpecBuilder.addModifiers(KModifier.OVERRIDE)
    }

    propertySpecBuilder.mutable(isMutable)

    val notMockedException = ClassName(NotMockedException::class.java.`package`.name, NotMockedException::class.simpleName!!)
    propertySpecBuilder.initializer("throw %T(%S)", notMockedException, "The property $propertyName is not mocked yet.")

    return propertySpecBuilder.build()
}