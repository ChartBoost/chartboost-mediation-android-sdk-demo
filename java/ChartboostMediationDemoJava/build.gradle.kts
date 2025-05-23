plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.chartboost.mediation.sdk.demo.java"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.chartboost.mediation.sdk.demo.java"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
        debug {
            isDebuggable = true
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // Chartboost SDK
    implementation(libs.chartboost.mediation.sdk)
    implementation(libs.chartboost.core.sdk)

    // Chartboost Mediation Adapters
    implementation(libs.chartboost.mediation.adapter.admob)
    implementation(libs.chartboost.mediation.adapter.amazon.publisher.services)
    implementation(libs.chartboost.mediation.adapter.applovin)
    implementation(libs.chartboost.mediation.adapter.chartboost)
    implementation(libs.chartboost.mediation.adapter.digital.turbine.exchange)
    implementation(libs.chartboost.mediation.adapter.google.bidding)
    implementation(libs.chartboost.mediation.adapter.hyprmx)
    implementation(libs.chartboost.mediation.adapter.inmobi)
    implementation(libs.chartboost.mediation.adapter.ironsource)
    implementation(libs.chartboost.mediation.adapter.meta.audience.network)
    implementation(libs.chartboost.mediation.adapter.mintegral)
    implementation(libs.chartboost.mediation.adapter.mobilefuse)
    implementation(libs.chartboost.mediation.adapter.pangle)
    implementation(libs.chartboost.mediation.adapter.reference)
    implementation(libs.chartboost.mediation.adapter.unity.ads)
    implementation(libs.chartboost.mediation.adapter.verve)
    implementation(libs.chartboost.mediation.adapter.vungle)

    // Lifecycle
    implementation(libs.lifecycle.common)
    implementation(libs.lifecycle.extensions)

    // Retrofit and Serialization
    implementation(libs.retrofit2.kotlinx.serialization.converter)
    implementation(libs.retrofit2.converter.scalars)
    implementation(libs.retrofit2.retrofit)

    // Kotlinimplementation(libs.kotlin.reflect)
    implementation(libs.kotlin.stdlib.jdk8)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlin.reflect)

    // OkHttp
    implementation(libs.okhttp3.logging.interceptor)
    implementation(libs.okhttp3.okhttp)

    // Google Play Services
    implementation(libs.play.services.base)
    implementation(libs.play.services.ads.identifier)
    implementation(libs.play.services.appset)
}
