package dev.tavieto.buildlogic

import com.android.build.api.dsl.CommonExtension
import dev.tavieto.buildlogic.extension.getLibrary
import dev.tavieto.buildlogic.extension.getVersion
import dev.tavieto.buildlogic.extension.implementation
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

internal fun Project.configureJetpackCompose(
    commonExtension: CommonExtension<*, *, *, *>
) {
    val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

    commonExtension.apply {
        buildFeatures.compose = true

        composeOptions {
            kotlinCompilerExtensionVersion = libs.getVersion("androidxComposeCompiler")
        }

        dependencies {
            with(libs) {
                implementation(platform(getLibrary("androidx-compose-bom")))
                implementation(getLibrary("androidx-compose-ui"))
                implementation(getLibrary("androidx-compose-ui-graphics"))
                implementation(getLibrary("androidx-compose-ui-util"))
                implementation(getLibrary("androidx-compose-ui-tooling"))
                implementation(getLibrary("androidx-compose-ui-tooling-preview"))
                implementation(getLibrary("androidx-compose-material"))
            }
        }
    }
}
