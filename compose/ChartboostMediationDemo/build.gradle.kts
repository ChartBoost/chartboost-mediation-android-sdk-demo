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
    implementation("com.chartboost:chartboost-mediation-sdk:4.7.0")

    // Chartboost Mediation adapters
    implementation("com.chartboost:chartboost-mediation-adapter-adcolony:4.+")
    implementation("com.chartboost:chartboost-mediation-adapter-admob:4.+")
    implementation("com.chartboost:chartboost-mediation-adapter-amazon-publisher-services:4.+")
    implementation("com.chartboost:chartboost-mediation-adapter-applovin:4.+")
    implementation("com.chartboost:chartboost-mediation-adapter-chartboost:4.+")
    implementation("com.chartboost:chartboost-mediation-adapter-digital-turbine-exchange:4.+")
    implementation("com.chartboost:chartboost-mediation-adapter-google-bidding:4.+")
    implementation("com.chartboost:chartboost-mediation-adapter-hyprmx:4.+")
    implementation("com.chartboost:chartboost-mediation-adapter-inmobi:4.+")
    implementation("com.chartboost:chartboost-mediation-adapter-ironsource:4.+")
    implementation("com.chartboost:chartboost-mediation-adapter-meta-audience-network:4.+")
    implementation("com.chartboost:chartboost-mediation-adapter-mintegral:4.+")
    implementation("com.chartboost:chartboost-mediation-adapter-mobilefuse:4.+")
    implementation("com.chartboost:chartboost-mediation-adapter-pangle:4.+")
    implementation("com.chartboost:chartboost-mediation-adapter-reference:4.+")
    implementation("com.chartboost:chartboost-mediation-adapter-tapjoy:4.+")
    implementation("com.chartboost:chartboost-mediation-adapter-unity-ads:4.+")
    implementation("com.chartboost:chartboost-mediation-adapter-verve:4.+")
    implementation("com.chartboost:chartboost-mediation-adapter-vungle:4.+")
    implementation("com.chartboost:chartboost-mediation-adapter-yahoo:4.+")

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
