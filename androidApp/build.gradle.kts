plugins {
    id("com.android.application")
    kotlin("android")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
}

android {
    namespace = "com.eleks.cah.android"
    compileSdk = 33
    defaultConfig {
        applicationId = "com.eleks.cah.android"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.0-alpha01"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
}

dependencies {
    implementation(project(":shared"))
    implementation("com.google.firebase:firebase-crashlytics-ktx:18.3.5")
    implementation("androidx.compose.ui:ui:1.3.3")
    implementation("androidx.compose.ui:ui-tooling:1.3.3")
    implementation("androidx.compose.ui:ui-tooling-preview:1.3.3")
    implementation("androidx.compose.foundation:foundation:1.3.1")
    implementation("androidx.compose.material:material:1.3.1")
    implementation("androidx.activity:activity-compose:1.6.1")
    implementation("androidx.navigation:navigation-compose:2.5.3")
    implementation("com.google.accompanist:accompanist-pager:0.29.1-alpha")
    implementation("com.google.accompanist:accompanist-navigation-animation:0.29.1-alpha")

    implementation("io.insert-koin:koin-core:3.3.2")
    implementation("io.insert-koin:koin-android:3.3.2")
    implementation("io.insert-koin:koin-androidx-compose:3.4.1")

}