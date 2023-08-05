plugins {
    id("dev.tavieto.android.library")
    id("kotlin-kapt")
}

android.namespace = "dev.tavieto.hearthstone.data.local"

dependencies {
    implementation(project(":core:commons"))
    implementation(project(":repository"))
    implementation(libs.koin.android)
    implementation(libs.androidx.datastore)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.gson)
    implementation(libs.androidx.room.runtime)
    kapt(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.paging)
}