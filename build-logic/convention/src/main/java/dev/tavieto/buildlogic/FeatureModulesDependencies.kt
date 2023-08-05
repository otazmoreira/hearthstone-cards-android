package dev.tavieto.buildlogic

import com.android.build.api.dsl.CommonExtension
import dev.tavieto.buildlogic.extension.getLibrary
import dev.tavieto.buildlogic.extension.implementation
import dev.tavieto.buildlogic.extension.testImplementation
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

internal fun Project.configureFeatureDependencies(
    commonExtension: CommonExtension<*, *, *, *>
) {
    val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
    commonExtension.apply {
        dependencies {
            implementation(project(":core:commons"))
            implementation(project(":core:core"))
            implementation(project(":core:uikit"))

            implementation(libs.getLibrary("koin-androidx-compose"))

            testImplementation(libs.getLibrary("junit"))
            testImplementation(libs.getLibrary("mockk"))
//            testImplementation(libs.getLibrary(""))
        }
    }
}
