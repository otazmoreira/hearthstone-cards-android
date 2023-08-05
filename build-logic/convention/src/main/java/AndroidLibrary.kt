import com.android.build.api.dsl.LibraryExtension
import dev.tavieto.buildlogic.configureAndroidDefaultSettings
import dev.tavieto.buildlogic.configureDetektStaticAnalysis
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

class AndroidLibrary: Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("com.android.library")
            pluginManager.apply("kotlin-android")

            val extension = extensions.getByType<LibraryExtension>()

            configureAndroidDefaultSettings(extension)
            configureDetektStaticAnalysis()
        }
    }
}
