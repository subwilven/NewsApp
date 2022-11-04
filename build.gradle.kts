// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {

    val compose_version by extra("1.1.0-beta01")
    val nav_version  by extra("2.5.3")

    repositories {
        google()
        mavenCentral()
        maven(url = " https://jitpack.io")
    }

    dependencies {
        classpath("com.android.tools.build:gradle:7.1.3")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.31")
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.42")
    }
}


plugins {
    id ("com.android.application") version "7.1.3" apply false
    id ("com.android.library") version "7.1.3" apply false
    id ("org.jetbrains.kotlin.android") version "1.5.31" apply false
}

tasks {
    register("clean", Delete::class) {
        delete(rootProject.buildDir)
    }
}