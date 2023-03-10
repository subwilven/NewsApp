// Top-level build file where you can add configuration options common to all sub-projects/modules.
allprojects {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

subprojects {
    apply(plugin = "io.gitlab.arturbosch.detekt")
    detekt {
        config = files("${project.rootDir}/detekt-config.yml")
        buildUponDefaultConfig = true
        parallel = true
    }
}


plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.dagger.hilt) apply false
    alias(libs.plugins.kotlin.kapt) apply false
    alias(libs.plugins.detekt.plugin)
}

tasks {
    register("clean", Delete::class) {
        delete(rootProject.buildDir)
    }
}
tasks.register<Copy>("copyGitHooksScripts") {
    from(layout.projectDirectory.dir("scripts"))
    into(layout.projectDirectory.dir(".git/hooks"))
}