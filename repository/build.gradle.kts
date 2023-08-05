@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.dev.tavieto.kotlin.domain)
}

dependencies {
    implementation(project(":core:commons"))
    api(project(":domain:cards"))
    implementation(libs.koin.core)
    implementation(libs.kotlinx.coroutines.core)
}
