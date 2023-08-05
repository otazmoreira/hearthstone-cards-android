package dev.tavieto.hearthstone.feature.main.util

import org.junit.Test
import java.lang.IllegalArgumentException

class HtmlUtilsTest {

    @Test
    fun `with bold tag simpleHtmlToAnnotatedString success`() {
        // GIVEN
        val htmlText = "<b>Deathrattle:</b> Summon a 5/5 Baine Bloodhoof."
        val expectedText = "Deathrattle: Summon a 5/5 Baine Bloodhoof."

        // WHEN
        val result = HtmlUtils.simpleHtmlToAnnotatedString(htmlText)
        val annotations = result.getStringAnnotations(0, expectedText.lastIndex)

        // THEN
        assert(result.text == expectedText)
        assert(annotations.size == 2)

        assert(annotations[0].item == "Deathrattle:")
        assert(annotations[0].start == 0)
        assert(annotations[0].end == 12)
        assert(annotations[0].tag == "BOLD")

        assert(annotations[1].item == " Summon a 5/5 Baine Bloodhoof.")
        assert(annotations[1].start == 12)
        assert(annotations[1].end == 42)
        assert(annotations[1].tag == "CLEAN")
    }

    @Test
    fun `with bold tag at the middle simpleHtmlToAnnotatedString success`() {
        // GIVEN
        val htmlText = "Deathrattle: <b>Summon</b> a 5/5 Baine Bloodhoof."
        val expectedText = "Deathrattle: Summon a 5/5 Baine Bloodhoof."

        // WHEN
        val result = HtmlUtils.simpleHtmlToAnnotatedString(htmlText)
        val annotations = result.getStringAnnotations(0, expectedText.lastIndex)

        // THEN
        assert(result.text == expectedText)
        assert(annotations.size == 3)

        assert(annotations[0].item == "Deathrattle: ")
        assert(annotations[0].start == 0)
        assert(annotations[0].end == 13)
        assert(annotations[0].tag == "CLEAN")

        assert(annotations[1].item == "Summon")
        assert(annotations[1].start == 13)
        assert(annotations[1].end == 19)
        assert(annotations[1].tag == "BOLD")

        assert(annotations[2].item == " a 5/5 Baine Bloodhoof.")
        assert(annotations[2].start == 19)
        assert(annotations[2].end == 42)
        assert(annotations[2].tag == "CLEAN")
    }

    @Test
    fun `with italic tag simpleHtmlToAnnotatedString success`() {
        // GIVEN
        val htmlText = "<i>Deathrattle:</i> Summon a 5/5 Baine Bloodhoof."
        val expectedText = "Deathrattle: Summon a 5/5 Baine Bloodhoof."

        // WHEN
        val result = HtmlUtils.simpleHtmlToAnnotatedString(htmlText)
        val annotations = result.getStringAnnotations(start = 0, end = expectedText.length)

        // THEN
        assert(result.text == expectedText)
        assert(annotations.size == 2)

        assert(annotations[0].item == "Deathrattle:")
        assert(annotations[0].start == 0)
        assert(annotations[0].end == 12)
        assert(annotations[0].tag == "ITALIC")

        assert(annotations[1].item == " Summon a 5/5 Baine Bloodhoof.")
        assert(annotations[1].start == 12)
        assert(annotations[1].end == 42)
        assert(annotations[1].tag == "CLEAN")
    }

    @Test
    fun `with underscore tag simpleHtmlToAnnotatedString success`() {
        // GIVEN
        val htmlText = "<u>Deathrattle:</u> Summon a 5/5 Baine Bloodhoof."
        val expectedText = "Deathrattle: Summon a 5/5 Baine Bloodhoof."

        // WHEN
        val result = HtmlUtils.simpleHtmlToAnnotatedString(htmlText)
        val annotations = result.getStringAnnotations(0, expectedText.lastIndex)

        // THEN
        assert(result.text == expectedText)
        assert(annotations.size == 2)

        assert(annotations[0].item == "Deathrattle:")
        assert(annotations[0].start == 0)
        assert(annotations[0].end == 12)
        assert(annotations[0].tag == "UNDERSCORE")

        assert(annotations[1].item == " Summon a 5/5 Baine Bloodhoof.")
        assert(annotations[1].start == 12)
        assert(annotations[1].end == 42)
        assert(annotations[1].tag == "CLEAN")
    }

    @Test
    fun `with unknown tag simpleHtmlToAnnotatedString success`() {
        // GIVEN
        val htmlText = "<unknown>Deathrattle:</unknown> Summon a 5/5 Baine Bloodhoof."
        val expectedText = "Deathrattle: Summon a 5/5 Baine Bloodhoof."

        // WHEN
        val result = HtmlUtils.simpleHtmlToAnnotatedString(htmlText)
        val annotations = result.getStringAnnotations(0, expectedText.lastIndex)

        // THEN
        assert(result.text == expectedText)
        assert(annotations.size == 2)

        assert(annotations[0].item == "Deathrattle:")
        assert(annotations[0].start == 0)
        assert(annotations[0].end == 12)
        assert(annotations[0].tag == "CLEAN")

        assert(annotations[1].item == " Summon a 5/5 Baine Bloodhoof.")
        assert(annotations[1].start == 12)
        assert(annotations[1].end == 42)
        assert(annotations[1].tag == "CLEAN")
    }

    @Test
    fun `with three tags simpleHtmlToAnnotatedString success`() {
        // GIVEN
        val htmlText = "<b>Deathrattle:</b> Summon <i>a 5/5</i> <u>Baine Bloodhoof.</u>"
        val expectedText = "Deathrattle: Summon a 5/5 Baine Bloodhoof."

        // WHEN
        val result = HtmlUtils.simpleHtmlToAnnotatedString(htmlText)
        val annotations = result.getStringAnnotations(0, expectedText.lastIndex)

        // THEN
        assert(result.text == expectedText)
        assert(annotations.size == 5)

        assert(annotations[0].item == "Deathrattle:")
        assert(annotations[0].start == 0)
        assert(annotations[0].end == 12)
        assert(annotations[0].tag == "BOLD")

        assert(annotations[1].item == " Summon ")
        assert(annotations[1].start == 12)
        assert(annotations[1].end == 20)
        assert(annotations[1].tag == "CLEAN")

        assert(annotations[2].item == "a 5/5")
        assert(annotations[2].start == 20)
        assert(annotations[2].end == 25)
        assert(annotations[2].tag == "ITALIC")

        assert(annotations[3].item == " ")
        assert(annotations[3].start == 25)
        assert(annotations[3].end == 26)
        assert(annotations[3].tag == "CLEAN")

        assert(annotations[4].item == "Baine Bloodhoof.")
        assert(annotations[4].start == 26)
        assert(annotations[4].end == 42)
        assert(annotations[4].tag == "UNDERSCORE")
    }

    @Test
    fun `with different open-close tags simpleHtmlToAnnotatedString success`() {
        // GIVEN
        val htmlText = "<open>Deathrattle:</close> Summon a 5/5 Baine Bloodhoof."
        val expectedText = "Deathrattle: Summon a 5/5 Baine Bloodhoof."

        // WHEN
        val result = HtmlUtils.simpleHtmlToAnnotatedString(htmlText)
        val annotations = result.getStringAnnotations(0, expectedText.lastIndex)

        // THEN
        assert(result.text == expectedText)
        assert(annotations.size == 2)

        assert(annotations[0].item == "Deathrattle:")
        assert(annotations[0].start == 0)
        assert(annotations[0].end == 12)
        assert(annotations[0].tag == "CLEAN")

        assert(annotations[1].item == " Summon a 5/5 Baine Bloodhoof.")
        assert(annotations[1].start == 12)
        assert(annotations[1].end == 42)
        assert(annotations[1].tag == "CLEAN")
    }

    @Test
    fun `with self-closing tag simpleHtmlToAnnotatedString success`() {
        // GIVEN
        val htmlText = "Deathrattle:<br/> Summon a 5/5 Baine Bloodhoof."
        val expectedText = "Deathrattle: Summon a 5/5 Baine Bloodhoof."

        // WHEN
        val result = HtmlUtils.simpleHtmlToAnnotatedString(htmlText)
        val annotations = result.getStringAnnotations(0, result.lastIndex)

        // THEN
        assert(result.text == expectedText)
        assert(annotations.size == 1)

        assert(annotations[0].item == "Deathrattle: Summon a 5/5 Baine Bloodhoof.")
        assert(annotations[0].start == 0)
        assert(annotations[0].end == 42)
        assert(annotations[0].tag == "CLEAN")
    }

    @Test(
        // THEN
        expected = IllegalArgumentException::class
    )
    fun `with incomplete open-close tag simpleHtmlToAnnotatedString failure`() {
        // GIVEN
        val htmlText = "Deathrattle:<b> Summon a 5/5 Baine Bloodhoof."

        // WHEN
        HtmlUtils.simpleHtmlToAnnotatedString(htmlText)
    }

    @Test
    fun `with incomplete tag simpleHtmlToAnnotatedString must succeed with the same value`() {
        // GIVEN
        val htmlText = "Deathrattle:<br Summon a 5/5 Baine Bloodhoof."

        // WHEN
        val result = HtmlUtils.simpleHtmlToAnnotatedString(htmlText)
        val annotations = result.getStringAnnotations(0, result.lastIndex)

        // THEN
        assert(result.text == htmlText)
        assert(annotations.size == 1)

        assert(annotations[0].item == "Deathrattle:<br Summon a 5/5 Baine Bloodhoof.")
        assert(annotations[0].start == 0)
        assert(annotations[0].end == 45)
        assert(annotations[0].tag == "CLEAN")
    }
}
