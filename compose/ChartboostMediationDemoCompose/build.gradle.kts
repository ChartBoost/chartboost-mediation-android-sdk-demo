plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.chartboost.mediation.sdk.demo"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.chartboost.mediation.sdk.demo"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.7"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // Chartboost Mediation SDK
    implementation("com.chartboost:chartboost-mediation-sdk:5.+")
    implementation("com.chartboost:chartboost-core-sdk:1.+")

    // Chartboost Mediation adapters
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

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.lifecycle:lifecycle-common:2.5.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.activity:activity-compose:1.7.2")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3:1.2.0-alpha02")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}
