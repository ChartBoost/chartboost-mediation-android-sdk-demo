pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven("https://cboost.jfrog.io/artifactory/chartboost-mediation/") {
            name = "Chartboost Mediation's Production Repo"
        }
        maven("https://cboost.jfrog.io/artifactory/chartboost-ads/") {
            name = "Chartboost's maven repo"
        }
        maven("https://android-sdk.is.com/") {
            name = "IronSource's maven repo"
        }
        maven("https://dl-maven-android.mintegral.com/repository/mbridge_android_sdk_oversea") {
            name = "Mintegral's maven repo"
        }
        maven("https://artifact.bytedance.com/repository/pangle") {
            name = "Pangle's maven repo"
        }
        maven("https://verve.jfrog.io/artifactory/verve-gradle-release") {
            name = "Verve's maven repo"
        }
    }
}

rootProject.name = "ChartboostMediationDemoCompose"
include(":ChartboostMediationDemoCompose")
