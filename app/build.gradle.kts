import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.example.metapolitan"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.metapolitan"
        minSdk = 25
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        val localProperties = Properties()
        localProperties.load(FileInputStream(rootProject.file("local.properties")))

        //return empty key in case something goes wrong
        val apiKey = localProperties.getProperty("API_KEY") ?: ""

        buildConfigField(
            type = "String",
            name = "API_KEY",
            value = apiKey
        )
    }

    buildTypes {
        debug {
            buildConfigField ("String", "BASE_URL", "\"https://api.themoviedb.org/\"")
            buildConfigField("String", "LARGE_IMAGE_URL", "\"https://image.tmdb.org/t/p/w500\"")
            android.buildFeatures.buildConfig = true
            isJniDebuggable = true
            isDebuggable = true
        }
        release {
            buildConfigField ("String", "BASE_URL", "\"https://api.themoviedb.org/\"")
            buildConfigField("String", "LARGE_IMAGE_URL", "\"https://image.tmdb.org/t/p/w500\"")
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation (libs.androidx.lifecycle.viewmodel.compose)
    implementation (libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Coroutine
    implementation(libs.kotlinx.coroutines.android)
    implementation (libs.kotlinx.coroutines.core)

    // Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
    implementation (libs.androidx.hilt.navigation.compose) // integrate hilt with compose navigation

    // Navigation
    implementation(libs.androidx.navigation.compose)

    //Retrofit
    implementation(libs.retrofit)
    implementation (libs.converter.gson)
    implementation(libs.logging.interceptor)
    implementation (libs.okhttp)

    // coil
    implementation (libs.coil.compose)

    // Paging
    implementation (libs.androidx.paging.runtime.ktx)
    implementation (libs.androidx.paging.compose)
}