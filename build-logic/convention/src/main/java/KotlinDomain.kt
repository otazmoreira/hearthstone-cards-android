import dev.tavieto.buildlogic.configureDetektStaticAnalysis
import dev.tavieto.buildlogic.extension.getLibrary
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.getByType

class KotlinDomain : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

            pluginManager.apply("kotlin")
            configureDetektStaticAnalysis()

            with(dependencies) {
                add("implementation", project(":core:commons"))
                add("implementation", libs.getLibrary("koin-core"))
                add("implementation", libs.getLibrary("kotlinx-coroutines-core"))
                add("testImplementation", libs.getLibrary("mockk"))
                add("testImplementation", libs.getLibrary("junit"))
                add("testImplementation", libs.getLibrary("kotlinx-coroutines-test"))
            }
        }
    }
}