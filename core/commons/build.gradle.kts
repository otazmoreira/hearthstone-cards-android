plugins {
    id("kotlin")
    id("kotlin-kapt")
}

dependencies {
    implementation(libs.koin.core)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.retrofit2.retrofit)
    implementation(libs.gson)
    testImplementation(libs.androidx.core.testing)
    testImplementation(libs.junit)
}
