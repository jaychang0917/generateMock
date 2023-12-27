plugins {
    kotlin("jvm")
    id("com.vanniktech.maven.publish")
}

val kspVersion: String by project
val kotlinPoetVersion: String by project

dependencies {
    implementation(project(":api"))
    implementation("com.google.devtools.ksp:symbol-processing-api:$kspVersion")
    implementation("com.squareup:kotlinpoet:$kotlinPoetVersion")
    implementation("com.squareup:kotlinpoet-ksp:$kotlinPoetVersion")
}