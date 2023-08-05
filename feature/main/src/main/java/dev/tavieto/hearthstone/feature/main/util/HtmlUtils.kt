package dev.tavieto.hearthstone.feature.main.util

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import dev.tavieto.hearthstone.core.commons.extension.Empty


object HtmlUtils {
    private const val TAG_PATTERN = "<(.*?)>"

    private fun String.getTags(): List<String> {
        val regex = Regex(TAG_PATTERN)
        val matches = regex.findAll(this)
        return matches.map { it.value }.toList()
    }

    private fun String.getTagsClassifieds(): List<TagClassified> {
        return getTags().map {
            TagClassified(
                tag = it,
                classification = TagClassification.getClassification(it)
            )
        }
    }

    private fun String.removeSelfClosingTags(list: List<TagClassified>): String {
        var result = this
        val selfClosingTags = list.filter {
            it.classification == TagClassification.SELF_CLOSING
        }

        selfClosingTags.forEach {
            val startPos = result.indexOf(it.tag)
            val endPost = startPos + it.tag.length
            result = result.removeRange(startIndex = startPos, endIndex = endPost)
        }

        return result
    }

    private fun String.simpleHtmlConversionOpenCloseTags(): HtmlConversionResult {
        val tags = getTagsClassifieds()
        val openTags = tags.filter { it.classification == TagClassification.OPEN }
        val closeTags = tags.filter { it.classification == TagClassification.CLOSE }

        require(openTags.size == closeTags.size) {
            "The text must to have the same count of open tag and close tag."
        }

        var result = this.removeSelfClosingTags(
            tags.filter { it.classification == TagClassification.SELF_CLOSING }
        )
        val tagsInfo = mutableListOf<TagInfo>()
        val classifications = tags.filter {
            it.classification != TagClassification.SELF_CLOSING
        }

        var count = 0
        while (count < classifications.size) {
            val startPos = result.indexOf(classifications[count].tag)
            result = result.removeRange(
                startIndex = startPos,
                endIndex = startPos + classifications[count].tag.length
            )

            val endPos = result.indexOf(classifications[count + 1].tag)
            result = result.removeRange(
                startIndex = endPos,
                endIndex = endPos + classifications[count + 1].tag.length
            )

            tagsInfo.add(
                TagInfo(
                    tag = Tag.getByValue(classifications[count].tag.cleanTag()),
                    start = startPos,
                    end = endPos - 1
                )
            )
            count += 2
        }

        var cleanPositions = tagsInfo.sortedBy { it.start }.getFillGap(result.length)

        if (tagsInfo.isEmpty()) {
            cleanPositions = listOf(
                TagInfo(
                    tag = Tag.CLEAN,
                    start = 0,
                    end = result.length - 1
                )
            )
        }

        return HtmlConversionResult(
            text = result,
            tagsInfo = (tagsInfo + cleanPositions).sortedBy { it.start }
        )
    }

    private fun List<TagInfo>.getFillGap(maxLength: Int): List<TagInfo> {
        val list = mutableListOf<TagInfo>()
        forEachIndexed { index, tagPositions ->
            if (index == 0) {
                if (tagPositions.start > 0) {
                    list.add(
                        TagInfo(
                            tag = Tag.CLEAN,
                            start = 0,
                            end = tagPositions.start - 1
                        )
                    )
                }
            } else {
                if (index == lastIndex) {
                    if (tagPositions.end < maxLength) {
                        val start = this[index - 1].end + 1
                        list.add(
                            TagInfo(
                                tag = Tag.CLEAN,
                                start = start,
                                end = tagPositions.start - 1
                            )
                        )
                    }
                } else {
                    val start = this[index - 1].end + 1
                    list.add(
                        TagInfo(
                            tag = Tag.CLEAN,
                            start = start,
                            end = tagPositions.start - 1
                        )
                    )
                }
            }
        }

        val last = lastOrNull()
        if (last?.end != maxLength - 1 && last != null) {
            list.add(
                TagInfo(
                    tag = Tag.CLEAN,
                    start = last.end + 1,
                    end = maxLength - 1
                )
            )
        }
        return list
    }

    private fun String.cleanTag(): String {
        var newString = this
        newString = newString.removePrefix("<")
        newString = newString.removePrefix("</")
        newString = newString.removeSuffix(">")
        newString = newString.removeSuffix("/>")
        return newString
    }

    private fun String.getInRange(start: Int, end: Int): String {
        var newString = String.Empty
        var counter = end - (end - start)
        while (counter <= end) {
            newString += get(counter)
            counter++
        }
        return newString
    }

    fun simpleHtmlToAnnotatedString(
        text: String,
        style: TextStyle = TextStyle.Default
    ): AnnotatedString {
        return buildAnnotatedString {
            val textConverted = text.simpleHtmlConversionOpenCloseTags()

            textConverted.tagsInfo.forEach {
                withStyle(
                    when (it.tag) {
                        Tag.BOLD -> style.copy(fontWeight = FontWeight.Bold)
                        Tag.ITALIC -> style.copy(fontStyle = FontStyle.Italic)
                        Tag.UNDERSCORE -> style.copy(textDecoration = TextDecoration.Underline)
                        Tag.CLEAN -> style
                    }.toSpanStyle()
                ) {
                    val textInRange = textConverted.text.getInRange(start = it.start, end = it.end)
                    pushStringAnnotation(it.tag.name, textInRange)
                    append(textInRange)
                    pop()
                }
            }
        }
    }
}

private data class TagClassified(
    val tag: String,
    val classification: TagClassification
)

private data class TagInfo(
    val tag: Tag,
    val start: Int,
    val end: Int
)

private data class HtmlConversionResult(
    val text: String,
    val tagsInfo: List<TagInfo>
)

private enum class TagClassification {
    OPEN, CLOSE, SELF_CLOSING;

    companion object {
        fun getClassification(value: String): TagClassification {
            if (value.contains("/>")) return SELF_CLOSING
            if (value.contains("</")) return CLOSE
            return OPEN
        }
    }
}

private enum class Tag(val value: String) {
    BOLD("b"), ITALIC("i"), UNDERSCORE("u"), CLEAN("clean");

    companion object {
        fun getByValue(value: String): Tag {
            return values().firstOrNull { it.value == value } ?: CLEAN
        }
    }
}
