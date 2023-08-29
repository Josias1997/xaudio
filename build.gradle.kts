// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        mavenCentral()
        google()
    }
    dependencies {
        classpath(libs.r8)
        classpath(libs.gradle)
        classpath(libs.kotlin.gradle.plugin)
        classpath(libs.androidx.navigation.safeargs.plugin)
    }
}

plugins {
    @Suppress("DSL_SCOPE_VIOLATION")
    alias(libs.plugins.github.ben.manes)
    @Suppress("DSL_SCOPE_VIOLATION")
    alias(libs.plugins.google.devtools.ksp) apply false
    id("com.android.application") version "8.1.1" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
}
