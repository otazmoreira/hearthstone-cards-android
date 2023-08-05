import com.android.build.api.dsl.LibraryExtension
import dev.tavieto.buildlogic.configureAndroidDefaultSettings
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType
import java.util.Properties

class AndroidLibrary: Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("com.android.library")
            pluginManager.apply("kotlin-android")

            val extension = extensions.getByType<LibraryExtension>()

            configureAndroidDefaultSettings(extension)
        }
    }
}
