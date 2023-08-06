@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.dev.tavieto.android.library)
    alias(libs.plugins.dev.tavieto.android.compose)
    id("kotlin-parcelize")
}
true

android.namespace = "dev.tavieto.hearthstone.core"

dependencies {
    api(projects.core.commons)
    implementation(libs.koin.core)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.kotlinx.coroutines.core)
}
