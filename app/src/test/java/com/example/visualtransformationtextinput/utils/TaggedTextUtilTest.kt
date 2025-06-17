package com.example.visualtransformationtextinput.utils

import com.example.visualtransformationtextinput.mapper.TaggedTextMapper
import com.example.visualtransformationtextinput.utils.TaggedTextUtil.changeTaggedText
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class TaggedTextUtilTest {

    private val mapper = TaggedTextMapper()

    @Test
    fun `changeTaggedText should handle removed symbol before tag`() {
        val textWithTags = "This text with <keyword>tag</keyword>"
        val editedText = "This text with tag"
        val taggedText = mapper.map(textWithTags)

        val restoredTaggedText = changeTaggedText(editedText, taggedText)

        assertThat(editedText).isEqualTo(restoredTaggedText.plainText)
        assertThat(restoredTaggedText.spans.size).isEqualTo(1)
    }

    @Test
    fun `changeTaggedText should handle added symbol before tag`() {
        val textWithTags = "This text with <keyword>tag</keyword>"
        val editedText = "This text with tag"
        val taggedText = mapper.map(textWithTags)

        val restoredTaggedText = changeTaggedText(editedText, taggedText)

        assertThat(editedText).isEqualTo(restoredTaggedText.plainText)
        assertThat(restoredTaggedText.spans.size).isEqualTo(1)
    }

    @Test
    fun `changeTaggedText should handle removed symbol after tag`() {
        val textWithTags = "This text with <keyword>tag</keyword> and another text"
        val editedText = "This text with tag  and another text"
        val taggedText = mapper.map(textWithTags)

        val restoredTaggedText = changeTaggedText(editedText, taggedText)

        assertThat(editedText).isEqualTo(restoredTaggedText.plainText)
        assertThat(restoredTaggedText.spans.size).isEqualTo(1)
    }

    @Test
    fun `changeTaggedText should handle added symbol after tag`() {
        val textWithTags = "This text with <keyword>tag</keyword> and another text"
        val editedText = "This text with tag  and another 1 text"
        val taggedText = mapper.map(textWithTags)

        val restoredTaggedText = changeTaggedText(editedText, taggedText)

        assertThat(editedText).isEqualTo(restoredTaggedText.plainText)
        assertThat(restoredTaggedText.spans.size).isEqualTo(1)
    }

    @Test
    fun `changeTaggedText should remove tags when symbol is removed inside keyword`() {
        val textWithTags = "This text with <keyword>tag</keyword> and another text"
        val editedText = "This text with tg  and another 1 text"
        val taggedText = mapper.map(textWithTags)

        val restoredTaggedText = changeTaggedText(editedText, taggedText)

        assertThat(editedText).isEqualTo(restoredTaggedText.plainText)
        assertThat(restoredTaggedText.spans).isEmpty()
    }

    @Test
    fun `changeTaggedText should remove tags when symbol is added inside keyword`() {
        val textWithTags = "This text with <keyword>tag</keyword> and another text"
        val editedText = "This text with taag  and another 1 text"
        val taggedText = mapper.map(textWithTags)

        val restoredTaggedText = changeTaggedText(editedText, taggedText)

        assertThat(editedText).isEqualTo(restoredTaggedText.plainText)
        assertThat(restoredTaggedText.spans).isEmpty()
    }

    @Test
    fun `changeTaggedText should handle removed group of symbols before tag`() {
        val textWithTags = "This text with <keyword>tag</keyword>"
        val editedText = "This t with tag"
        val taggedText = mapper.map(textWithTags)

        val restoredTaggedText = changeTaggedText(editedText, taggedText)

        assertThat(editedText).isEqualTo(restoredTaggedText.plainText)
        assertThat(restoredTaggedText.spans.size).isEqualTo(1)
    }

    @Test
    fun `changeTaggedText should handle removed group of symbols after tag`() {
        val textWithTags = "This text with <keyword>tag</keyword> and another text"
        val editedText = "This text with tag  and another t"
        val taggedText = mapper.map(textWithTags)

        val restoredTaggedText = changeTaggedText(editedText, taggedText)

        assertThat(editedText).isEqualTo(restoredTaggedText.plainText)
        assertThat(restoredTaggedText.spans.size).isEqualTo(1)
    }

    @Test
    fun `changeTaggedText should handle removed symbols between two keywords`() {
        val textWithTags = "This text with <keyword>tag</keyword> and another text with <keyword>keyword</keyword>"
        val editedText = "This text with tag  and another 1 text with keyword"
        val taggedText = mapper.map(textWithTags)

        val restoredTaggedText = changeTaggedText(editedText, taggedText)

        assertThat(editedText).isEqualTo(restoredTaggedText.plainText)
        assertThat(restoredTaggedText.spans.size).isEqualTo(2)
    }

    @Test
    fun `changeTaggedText should remove tag when symbol is added right before keyword`() {
        val textWithTags = "This text with <keyword>tag</keyword>"
        val editedText = "This text with 1tag"

        val taggedText = mapper.map(textWithTags)

        val restoredTaggedText = changeTaggedText(editedText, taggedText)

        assertThat(restoredTaggedText.spans).isEmpty()
    }

    @Test
    fun `changeTaggedText should remove tag when symbol is added at the end of keyword`() {
        val textWithTags = "This text with <keyword>tag</keyword>"
        val editedText = "This text with tag1"
        val taggedText = mapper.map(textWithTags)

        val restoredTaggedText = changeTaggedText(editedText, taggedText)

        assertThat(restoredTaggedText.spans).isEmpty()
    }
}