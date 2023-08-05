@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.dev.tavieto.android.feature)
    id("kotlin-parcelize")
}
true

android.namespace = "dev.tavieto.hearthstone.feature.main"

dependencies {
    implementation(project(":domain:cards"))
    implementation(platform(libs.kotlin.bom))
    implementation(libs.landscapist.glide)
    implementation(libs.lottie.compose)
}
