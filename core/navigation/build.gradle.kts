plugins {
    id("dev.tavieto.android.library")
    id("dev.tavieto.android.compose")
}

android.namespace = "dev.tavieto.hearthstone.core.navigation"

dependencies {
    implementation(project(":core:commons"))

    // project features
    implementation(project(":feature:main"))

    // navigation
    api(libs.androidx.navigation)
    api(libs.androidx.navigation.common.ktx)

    // dependency injection
    implementation(libs.koin.androidx.compose)

    // gson
    implementation(libs.gson)
}