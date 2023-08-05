plugins {
    id("dev.tavieto.android.library")
}

android {
    namespace = "dev.tavieto.hearthstone.data.remote"
}

dependencies {
    implementation(project(":core:commons"))
    implementation(project(":repository"))
    implementation(libs.koin.core)
    implementation(libs.retrofit2.converter.gson)
    implementation(libs.retrofit2.retrofit)
    implementation(libs.okhttp3.okhttp)
    implementation(libs.okhttp3.logging.interception)
    implementation(libs.kotlinx.coroutines.core)
}