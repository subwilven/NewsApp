// Top-level build file where you can add configuration options common to all sub-projects/modules.
allprojects {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
tasks {
    register("clean", Delete::class) {
        delete(rootProject.buildDir)
    }
}
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.dagger.hilt) apply false
    alias(libs.plugins.kotlin.kapt) apply false
    alias(libs.plugins.detekt.plugin) apply false
}
