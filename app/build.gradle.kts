import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.dagger.hilt)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.detekt.plugin)
}
val apiKey: String = gradleLocalProperties(rootDir).getProperty("API_KEY")
android {
    compileSdk = 34
    namespace = "com.example.newsapp"
    defaultConfig {

        applicationId = "com.example.newsapp"
        minSdk = 21
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions.jvmTarget = "17"
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
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

    testImplementation(libs.bundles.jupiter)
    testImplementation(libs.junit)
    androidTestImplementation(libs.compose.test)
    androidTestImplementation(libs.junit.ext)
    androidTestImplementation(libs.espresso)
}