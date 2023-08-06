package dev.tavieto.buildlogic

import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

internal fun Project.configureDetektStaticAnalysis() {

    pluginManager.apply("io.gitlab.arturbosch.detekt")
//
    val detektExtension = extensions.getByType<DetektExtension>()
//
//    with(detektExtension) {
//        toolVersion = "1.23.1"
//        autoCorrect = true
//        parallel = true
//        ignoreFailures = false
//        buildUponDefaultConfig = true
//        config.setFrom(files("$rootDir/config/detekt/detekt.yml"))
//    }
}