plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.chartboost.mediation.sdk.demo.java"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.chartboost.mediation.sdk.demo.java"
        minSdk = 21
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
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildFeatures {
        viewBinding = true
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

    implementation("com.chartboost:chartboost-mediation-sdk:5.2.0")
    implementation("com.chartboost:chartboost-core-sdk:1.+")


//     Chartboost Mediation adapters
    implementation("com.chartboost:chartboost-mediation-adapter-admob:5.+")
    implementation("com.chartboost:chartboost-mediation-adapter-amazon-publisher-services:5.+")
    implementation("com.chartboost:chartboost-mediation-adapter-applovin:5.+")
    implementation("com.chartboost:chartboost-mediation-adapter-chartboost:5.+")
    implementation("com.chartboost:chartboost-mediation-adapter-digital-turbine-exchange:5.+")
    implementation("com.chartboost:chartboost-mediation-adapter-google-bidding:5.+")
    implementation("com.chartboost:chartboost-mediation-adapter-hyprmx:5.+")
    implementation("com.chartboost:chartboost-mediation-adapter-inmobi:5.+")
    implementation("com.chartboost:chartboost-mediation-adapter-ironsource:5.+")
    implementation("com.chartboost:chartboost-mediation-adapter-meta-audience-network:5.+")
    implementation("com.chartboost:chartboost-mediation-adapter-mintegral:5.+")
    implementation("com.chartboost:chartboost-mediation-adapter-mobilefuse:5.+")
    implementation("com.chartboost:chartboost-mediation-adapter-pangle:5.+")
    implementation("com.chartboost:chartboost-mediation-adapter-reference:5.+")
    implementation("com.chartboost:chartboost-mediation-adapter-unity-ads:5.+")
    implementation("com.chartboost:chartboost-mediation-adapter-verve:5.+")
    implementation("com.chartboost:chartboost-mediation-adapter-vungle:5.+")

    implementation("androidx.lifecycle:lifecycle-common:2.6.2")
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:1.0.0")
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.8.10")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.9.21")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")
    implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")
    implementation("com.squareup.okhttp3:okhttp:4.11.0")
    implementation("com.squareup.retrofit2:converter-scalars:2.9.0")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")


    implementation("com.google.android.gms:play-services-base:18.1.0")
    implementation("com.google.android.gms:play-services-ads-identifier:18.0.1")
    implementation("com.google.android.gms:play-services-appset:16.0.2")
}
