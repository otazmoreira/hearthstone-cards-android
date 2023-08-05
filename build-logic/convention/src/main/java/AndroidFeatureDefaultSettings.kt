import com.android.build.gradle.LibraryExtension
import dev.tavieto.buildlogic.configureAndroidDefaultSettings
import dev.tavieto.buildlogic.configureFeatureDependencies
import dev.tavieto.buildlogic.configureJetpackCompose
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

class AndroidFeatureDefaultSettings: Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("com.android.library")
            pluginManager.apply("kotlin-android")

            val extension = extensions.getByType<LibraryExtension>()

            configureAndroidDefaultSettings(extension)
            configureJetpackCompose(extension)
            configureFeatureDependencies(extension)
        }
    }
}
