@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.dev.tavieto.kotlin.domain)
}

dependencies {
    implementation(projects.core.commons)
    api(projects.domain.cards)
    implementation(libs.koin.core)
    implementation(libs.kotlinx.coroutines.core)
}
