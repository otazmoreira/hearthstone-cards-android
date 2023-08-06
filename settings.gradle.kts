pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "Hearthstone-Cards"
include(":app")
include(":core:core")
include(":core:commons")
include(":core:navigation")
include(":core:uikit")
include(":data:local")
include(":data:remote")
include(":feature:main")
include(":repository")
include(":domain:cards")
