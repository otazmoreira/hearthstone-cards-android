plugins {
    id("dev.tavieto.android.library")
    id("dev.tavieto.android.compose")
    id("kotlin-parcelize")
}

android.namespace = "dev.tavieto.hearthstone.core"

dependencies {
    api(project(":core:commons"))
    implementation(libs.koin.core)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.gson)
    implementation(libs.kotlinx.coroutines.core)

    implementation(libs.accompanist.pager)
    implementation(libs.accompanist.insetsui)
    implementation(libs.accompanist.flowlayout)
    implementation(libs.accompanist.navigation)
    implementation(libs.accompanist.permissions)
    implementation(libs.accompanist.systemuicontroller)
    implementation(libs.androidx.compose.foundation)

    testImplementation(libs.androidx.core.testing)
    testImplementation(libs.junit)
}
