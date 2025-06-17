package com.example.visualtransformationtextinput.mapper

import com.google.common.truth.Truth.assertThat
import org.junit.Test


class TaggedTextMapperTest {

    private val sut = TaggedTextMapper()

    @Test
    fun `map should return plainText without tags`() {
        val input = "This is <keyword>important</keyword> text"

        val result = sut.map(input)

        assertThat(result.plainText).isEqualTo("This is important text")
    }

    @Test
    fun `map should create one span for one keyword`() {
        val input = "This is <keyword>important</keyword> text"

        val result = sut.map(input)

        assertThat(result.spans.size).isEqualTo(1)
        assertThat(result.spans[0].start).isEqualTo(8)
        assertThat(result.spans[0].end).isEqualTo(17)
    }

    @Test
    fun `map should handle multiple keyword tags`() {
        val input = "<keyword>First</keyword> and <keyword>Second</keyword>"

        val result = sut.map(input)

        assertThat(result.plainText).isEqualTo("First and Second")
        assertThat(result.spans).hasSize(2)
        assertThat(result.spans[0].start).isEqualTo(0)
        assertThat(result.spans[0].end).isEqualTo(5)
        assertThat(result.spans[1].start).isEqualTo(10)
        assertThat(result.spans[1].end).isEqualTo(16)
    }

    @Test
    fun `map should return empty span list when there are no tags`() {
        val input = "Just a normal sentence with no tags"

        val result = sut.map(input)

        assertThat(result.plainText).isEqualTo(input)
        assertThat(result.spans).isEmpty()
    }

    @Test
    fun `map should ignore malformed tags`() {
        val input = "Some <keyword>broken<keyword> tag"

        val result = sut.map(input)

        assertThat(result.plainText).contains("broken")
        assertThat(result.spans).isEmpty()
    }
}