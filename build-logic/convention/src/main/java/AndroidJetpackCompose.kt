import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.LibraryExtension
import dev.tavieto.buildlogic.configureJetpackCompose
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

class AndroidJetpackCompose: Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("kotlin-android")

            val extension: CommonExtension<*, *, *, *> = try {
                extensions.getByType<LibraryExtension>()
            } catch (_: Throwable) {
                extensions.getByType<ApplicationExtension>()
            }

            configureJetpackCompose(extension)
        }
    }
}