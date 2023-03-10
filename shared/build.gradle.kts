import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING

plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization") version "1.8.10"
    id("com.android.library")
    id("com.codingfeline.buildkonfig").version("0.13.3")
}

kotlin {
    android()

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
            isStatic = true
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-core:2.2.3")
                implementation("io.insert-koin:koin-core:3.3.2")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.1")
                implementation("io.github.aakira:napier:2.6.1")
                implementation("dev.gitlive:firebase-auth:1.6.2")
                implementation("dev.gitlive:firebase-database:1.6.2")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val androidMain by getting {
            dependencies {
                implementation("io.insert-koin:koin-android:3.3.2")
                implementation("io.insert-koin:koin-androidx-compose:3.4.1")
                implementation("androidx.compose.ui:ui:1.3.3")
                implementation("androidx.compose.ui:ui-tooling:1.3.3")
                implementation("androidx.compose.ui:ui-tooling-preview:1.3.3")
                implementation("androidx.compose.foundation:foundation:1.3.1")
                implementation("androidx.compose.material:material:1.3.1")
                implementation("androidx.activity:activity-compose:1.6.1")
                implementation("io.ktor:ktor-client-okhttp:2.2.3")
            }
        }
        val androidTest by getting
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
            dependencies {
                implementation("io.ktor:ktor-client-darwin:2.2.3")
            }
        }
        val iosX64Test by getting
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting
        val iosTest by creating {
            dependsOn(commonTest)
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)
        }
    }
}

buildkonfig {
    packageName = "com.eleks.cah"
    defaultConfigs {
        buildConfigField(STRING, "OPEN_API_KEY", System.getenv("OPEN_API_KEY") ?: "")
    }
}

android {
    namespace = "com.eleks.cah"
    compileSdk = 33
    defaultConfig {
        minSdk = 24
        targetSdk = 33
    }
}