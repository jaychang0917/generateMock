pluginManagement {
    val kotlinVersion: String by settings
    val kspVersion: String by settings
    val publishVersion: String by settings
    plugins {
        id("com.google.devtools.ksp") version kspVersion
        kotlin("jvm") version kotlinVersion
        id("com.vanniktech.maven.publish") version publishVersion
    }
    repositories {
        gradlePluginPortal()
        google()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "generateMock"

include(":api")
include(":processor")
include(":sample")