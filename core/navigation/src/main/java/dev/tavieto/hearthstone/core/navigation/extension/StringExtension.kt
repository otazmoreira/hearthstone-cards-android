package dev.tavieto.hearthstone.core.navigation.extension

import java.net.URLDecoder
import java.net.URLEncoder

fun String.encode(): String = URLEncoder.encode(this, "UTF-8")
fun String.decode(): String = URLDecoder.decode(this, "UTF-8")
