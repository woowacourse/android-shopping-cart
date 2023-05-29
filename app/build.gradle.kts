plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
}

android {
    namespace = "woowacourse.shopping"
    compileSdk = 33

    defaultConfig {
        applicationId = "woowacourse.shopping"
        minSdk = 26
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    dataBinding {
        enable = true
    }
    viewBinding {
        enable = true
    }
}

dependencies {
    // 프로젝트내 의존성
    implementation(project(":domain"))

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.0")
    implementation("com.google.android.material:material:1.7.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    testImplementation("org.assertj:assertj-core:3.22.0")

    // Glide
    implementation("com.github.bumptech.glide:glide:4.15.1")

    // Mockk
    testImplementation("io.mockk:mockk:1.13.5")

    // concatAdapter
    implementation("androidx.recyclerview:recyclerview:1.3.0")

    // MockWebServer
    implementation("com.squareup.okhttp3:mockwebserver:4.11.0")

    // Gson
    implementation("com.google.code.gson:gson:2.10.1")

    // okhttp
    implementation("com.squareup.okhttp3:okhttp:4.11.0")
}
