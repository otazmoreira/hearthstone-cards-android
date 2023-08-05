package dev.tavieto.hearthstone.core.commons.extension

import java.net.URI

fun URI.updateScheme(newScheme: String): URI {
    return URI(newScheme, userInfo, host, port, path, query, fragment)
}
