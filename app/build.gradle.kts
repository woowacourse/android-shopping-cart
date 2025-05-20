plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.android.junit5)
    alias(libs.plugins.kotlin.android)
    id("kotlin-kapt")
    id("kotlin-parcelize")
}

android {
    namespace = "woowacourse.shopping"
    compileSdk = 35

    defaultConfig {
        applicationId = "woowacourse.shopping"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
        buildConfigField("String", "DB_NAME", "\"shopping_db\"")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        testInstrumentationRunnerArguments["runnerBuilder"] = "de.mannodermaus.junit5.AndroidJUnit5Builder"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
            buildConfigField("String", "DB_NAME", "\"shopping_release_db\"")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
    kotlinOptions {
        jvmTarget = "21"
    }
    packaging {
        resources {
            excludes += "META-INF/**"
            excludes += "win32-x86*/**"
        }
    }
    buildFeatures {
        dataBinding = true
        buildConfig = true
    }
}

dependencies {
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.core.ktx)
    implementation(libs.google.material)
    implementation(libs.glide)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.activity.ktx.v182)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.recyclerview)
    testImplementation(libs.assertj.core)
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.kotest.runner.junit5)
    testImplementation(libs.androidx.core.testing)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.test.runner)
    androidTestImplementation(libs.assertj.core)
    androidTestImplementation(libs.junit.jupiter)
    androidTestImplementation(libs.kotest.runner.junit5)
    androidTestImplementation(libs.mannodermaus.junit5.core)
    androidTestRuntimeOnly(libs.mannodermaus.junit5.runner)
    androidTestImplementation(libs.androidx.espresso.contrib)
    androidTestImplementation(libs.espresso.intents)
    androidTestImplementation(libs.androidx.core.testing.v200)
    kapt(libs.compiler)
    kapt(libs.androidx.room.compiler)
}
