@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.dev.tavieto.android.library)
    alias(libs.plugins.dev.tavieto.android.compose)
}
true

android.namespace = "dev.tavieto.hearthstone.core.uikit"

dependencies {
    implementation(libs.landscapist.glide)
}