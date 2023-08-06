package dev.tavieto.buildlogic

import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import java.util.Properties

internal fun Project.configureAndroidDefaultSettings(
    commonExtension: LibraryExtension
) {

    val secrets = Properties()
    secrets.load(this.project.rootProject.file("secrets.properties").inputStream())

    commonExtension.apply {
        compileSdk = 33
        defaultConfig {
            minSdk = 24
            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
            vectorDrawables.useSupportLibrary = true
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_11
            targetCompatibility = JavaVersion.VERSION_11
        }

        sourceSets {
            getByName("main").java.srcDir("src/main/kotlin")
        }

        buildTypes {
            getByName("release") {
                isMinifyEnabled = true
                isJniDebuggable = false
                proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), file("proguard-rules.pro"))
            }
            getByName("debug") {
                isMinifyEnabled = false
                isJniDebuggable = true
                proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), file("proguard-rules.pro"))
            }
        }

        buildTypes.all {
            buildConfigField(
                type = "String",
                name = "RAPID_API_KEY",
                value = secrets.getProperty("RAPID_API_KEY")
            )
            buildConfigField(
                type = "String",
                name = "RAPID_API_HOST",
                value = secrets.getProperty("RAPID_API_HOST")
            )
            buildConfigField(
                type = "String",
                name = "RAPID_API_URL",
                value = secrets.getProperty("RAPID_API_URL")
            )
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_11
            targetCompatibility = JavaVersion.VERSION_11
        }
    }
}
