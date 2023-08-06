plugins {
    `kotlin-dsl`
}

group = "dev.tavieto.buildlogic.convention"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    compileOnly("com.android.tools.build:gradle:8.0.1")
    compileOnly("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:1.22.0")
}

gradlePlugin {
    plugins {
        register("AndroidLibrary") {
            id = "dev.tavieto.android.library"
            implementationClass = "AndroidLibrary"
            version = "1.0.0"
        }
        register("JetpackComposeConfig") {
            id = "dev.tavieto.android.compose"
            implementationClass = "AndroidJetpackCompose"
            version = "1.0.0"
        }
        register("AndroidFeature") {
            id = "dev.tavieto.android.feature"
            implementationClass = "AndroidFeatureDefaultSettings"
            version = "1.0.0"
        }
        register("KotlinDomain") {
            id = "dev.tavieto.kotlin.domain"
            implementationClass = "KotlinDomain"
            version = "1.0.0"
        }
    }
}