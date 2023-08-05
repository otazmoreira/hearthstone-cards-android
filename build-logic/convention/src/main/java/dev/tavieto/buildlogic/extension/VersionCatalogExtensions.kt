package dev.tavieto.buildlogic.extension

import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.provider.Provider

internal fun VersionCatalog.getVersion(alias: String): String {
    return findVersion(alias).get().toString()
}

internal fun VersionCatalog.getLibrary(alias: String): Provider<MinimalExternalModuleDependency> {
    return findLibrary(alias).get()
}
