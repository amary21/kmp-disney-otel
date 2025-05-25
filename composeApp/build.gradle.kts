import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinserialization)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlinCocoapods)
    alias(libs.plugins.swiftKLib)
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "composeApp"
            isStatic = true
        }
    }

    cocoapods {
        homepage = "Disney Character App"
        summary = "Demo of OpenTelemetry on Kotlin Multiplatform with Grafana, using https://disneyapi.dev for distributed tracing and metrics."
        version = "1.0"
        ios.deploymentTarget = "15.3"
        podfile = project.file("../iosApp/Podfile")

        framework {
            baseName = "composeApp"
            compilerOptions.optIn.add("-Xbinary=bundleId=com.amary.disney.character.disneychar")
            isStatic = true
        }

        pod("OpenTelemetry-Swift-Sdk") {
            moduleName = "OpenTelemetrySdk"
            extraOpts += listOf("-compiler-option", "-fmodules")
        }

        pod("OpenTelemetry-Swift-Api") {
            moduleName = "OpenTelemetryApi"
            extraOpts += listOf("-compiler-option", "-fmodules")
        }
        pod("OpenTelemetry-Swift-Protocol-Exporter-Common") {
            moduleName = "OpenTelemetryProtocolExporterCommon"
            extraOpts += listOf("-compiler-option", "-fmodules")
        }
        pod("OpenTelemetry-Swift-Protocol-Exporter-Http") {
            moduleName = "OpenTelemetryProtocolExporterHttp"
            extraOpts += listOf("-compiler-option", "-fmodules")
        }
        pod("OpenTelemetry-Swift-StdoutExporter") {
            moduleName = "StdoutExporter"
            extraOpts += listOf("-compiler-option", "-fmodules")
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
            implementation(libs.ktor.client.okhttp)
            implementation(project.dependencies.platform(libs.opentelemetry.bom))
            implementation(libs.opentelemetry.api)
            implementation(libs.opentelemetry.context)
            implementation(libs.opentelemetry.exporter.otlp)
            implementation(libs.opentelemetry.exporter.logging)
            implementation(libs.opentelemetry.extension.kotlin)
            implementation(libs.opentelemetry.sdk)
            implementation(libs.opentelemetry.semconv)
            implementation(libs.opentelemetry.semconv.incubating)
        }

        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }

        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtimeCompose)
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.logging)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)
            implementation(libs.koin.compose.viewmodel.navigation)
            implementation(libs.kermit)
            implementation(libs.androidx.navigation.compose)
            implementation(libs.coil.compose)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }

        all {
            languageSettings {
                optIn("kotlinx.cinterop.ExperimentalForeignApi")
            }
        }
    }
}

android {
    namespace = "com.amary.disney.character.disneychar"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.amary.disney.character.disneychar"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    debugImplementation(compose.uiTooling)
}