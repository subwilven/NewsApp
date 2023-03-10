import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.dagger.hilt)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.detekt.plugin)
}
val apiKey: String = gradleLocalProperties(rootDir).getProperty("api.key")
@Suppress("UnstableApiUsage")
android {
    compileSdk  = 33

    defaultConfig {

        applicationId ="com.example.newsapp"
        minSdk = 21
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"


        buildConfigField("String", "API_KEY", apiKey)
        vectorDrawables {
            useSupportLibrary = true
        }

    }
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions.jvmTarget = "1.8"
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}
//for dagger hilt
kapt {
    correctErrorTypes = true
}

dependencies {

    implementation(libs.compose.ui)
    implementation(libs.compose.runtime)
    implementation(libs.bundles.core)
    implementation(libs.bundles.compose.accompanist)
    implementation(libs.bundles.lifecycle)
    implementation(libs.bundles.compose.material)
    implementation(libs.activity.compose)
    implementation(libs.bundles.hilt)
    kapt(libs.dagger.compiler)
    implementation(libs.coil)
    implementation(libs.bundles.retrofit)
    implementation(libs.bundles.navigation)
    implementation(libs.bundles.room)
    kapt(libs.room.compiler)
    implementation(libs.bundles.paging)

    testImplementation(libs.junit)
    androidTestImplementation(libs.compose.test)
    androidTestImplementation(libs.junit.ext)
    androidTestImplementation(libs.espresso)
}