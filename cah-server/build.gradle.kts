val ktor_version = "2.2.2"

plugins {
    application
    kotlin("jvm")
    id("io.ktor.plugin") version "2.2.2"
}

application {
    mainClass.set("com.eleks.cah_server.ServerKt")
}

dependencies {
    implementation(project(":shared"))

    implementation("io.ktor:ktor-server-core-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-netty-jvm:$ktor_version")
}