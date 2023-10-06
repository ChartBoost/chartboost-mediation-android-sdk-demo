plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.chartboost.mediation.sdk.demo"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.chartboost.mediation.sdk.demo"
        minSdk = 21
        targetSdk = 33
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
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // Chartboost Mediation SDK
    implementation("com.chartboost:chartboost-mediation-sdk:4.5.0")

    // Chartboost Mediation adapters
    implementation("com.chartboost:chartboost-mediation-adapter-adcolony:+")
    implementation("com.chartboost:chartboost-mediation-adapter-admob:+")
    implementation("com.chartboost:chartboost-mediation-adapter-amazon-publisher-services:+")
    implementation("com.chartboost:chartboost-mediation-adapter-applovin:+")
    implementation("com.chartboost:chartboost-mediation-adapter-chartboost:+")
    implementation("com.chartboost:chartboost-mediation-adapter-digital-turbine-exchange:+")
    implementation("com.chartboost:chartboost-mediation-adapter-google-bidding:+")
    implementation("com.chartboost:chartboost-mediation-adapter-hyprmx:+")
    implementation("com.chartboost:chartboost-mediation-adapter-inmobi:+")
    implementation("com.chartboost:chartboost-mediation-adapter-ironsource:+")
    implementation("com.chartboost:chartboost-mediation-adapter-meta-audience-network:+")
    implementation("com.chartboost:chartboost-mediation-adapter-mintegral:+")
    implementation("com.chartboost:chartboost-mediation-adapter-mobilefuse:+")
    implementation("com.chartboost:chartboost-mediation-adapter-pangle:+")
    implementation("com.chartboost:chartboost-mediation-adapter-reference:+")
    implementation("com.chartboost:chartboost-mediation-adapter-tapjoy:+")
    implementation("com.chartboost:chartboost-mediation-adapter-unity-ads:4.4.8.0.2")
    implementation("com.chartboost:chartboost-mediation-adapter-verve:+")
    implementation("com.chartboost:chartboost-mediation-adapter-vungle:+")
    implementation("com.chartboost:chartboost-mediation-adapter-yahoo:+")

    implementation("androidx.core:core-ktx:1.9.0")
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
