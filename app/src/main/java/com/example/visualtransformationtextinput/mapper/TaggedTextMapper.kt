package com.example.visualtransformationtextinput.mapper

import com.example.component.models.Span
import com.example.component.models.TaggedTextModel
import com.example.visualtransformationtextinput.utils.KEYWORD_TAG

private const val KEYWORD_TAGS_PATTERN = "<$KEYWORD_TAG>(.*?)</$KEYWORD_TAG>"

class TaggedTextMapper {

    fun map(text: String): TaggedTextModel {
        val regex = KEYWORD_TAGS_PATTERN.toRegex()
        val plainText = StringBuilder()
        val spans = mutableListOf<Span>()
        var lastIndex = 0

        regex.findAll(text).forEach { match ->
            val beforeTag = text.substring(lastIndex, match.range.first)
            plainText.append(beforeTag)

            val start = plainText.length
            val keyword = match.groupValues[1]
            plainText.append(keyword)
            val end = plainText.length

            spans.add(Span(start, end, KEYWORD_TAG))

            lastIndex = match.range.last + 1
        }

        plainText.append(text.substring(lastIndex))

        return TaggedTextModel(plainText.toString(), spans)
    }
}