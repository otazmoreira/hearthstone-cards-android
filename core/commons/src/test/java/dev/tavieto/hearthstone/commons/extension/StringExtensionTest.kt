package dev.tavieto.hearthstone.commons.extension

import dev.tavieto.hearthstone.core.commons.extension.checkAndFixUrlScheme
import org.junit.Assert.assertTrue
import org.junit.Test

class StringExtensionTest {

    @Test
    fun test() {
        val url = "http://wow.zamimg.com/images/hearthstone/cards/enus/animated/EX1_572_premium.gif"
        val urlCorrect = "https://wow.zamimg.com/images/hearthstone/cards/enus/animated/EX1_572_premium.gif"
        val newUrl = url.checkAndFixUrlScheme()
        println(newUrl)
        assertTrue(urlCorrect == newUrl)
        assertTrue(url != newUrl)
    }
}
