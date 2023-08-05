package dev.tavieto.hearthstone.core.commons.extension

import java.net.URI
import java.nio.charset.StandardCharsets

private const val HTTPS_SCHEME = "https"

val String.Companion.Empty
    get() = ""

fun String.checkAndFixUrlScheme(scheme: String = HTTPS_SCHEME): String {
    return try {
        val uri = URI.create(this)
        if (uri.scheme == scheme) return this
        val newUri = uri.updateScheme(newScheme = scheme)
        if (isAsciiString()) newUri.toASCIIString() else newUri.toString()
    } catch (_: Throwable) {
        this
    }
}

fun String.isAsciiString(): Boolean {
    return this == this.toByteArray().toString(StandardCharsets.US_ASCII)
}
