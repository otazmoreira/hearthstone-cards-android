import dev.tavieto.buildlogic.extension.getLibrary
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType

class KotlinDomain : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

            pluginManager.apply("kotlin")
            with(dependencies) {
                add("implementation", project(":core:commons"))
                add("implementation", libs.getLibrary("koin-core"))
                add("implementation", libs.getLibrary("kotlinx-coroutines-core"))
            }
        }
    }
}