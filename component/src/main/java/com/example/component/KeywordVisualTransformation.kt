package com.example.component

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import com.example.component.models.TaggedTextModel

internal class KeywordVisualTransformation(
    private val taggedText: TaggedTextModel,
    private val color: Color
) : VisualTransformation {

    override fun filter(text: AnnotatedString): TransformedText {
        val annotatedString = buildAnnotatedString {
            var lastIndex = 0
            taggedText.spans.forEach { span ->
                append(text.substring(lastIndex, span.start))

                if (span.start < text.length) {
                    withStyle(style = SpanStyle(color = color)) {
                        append(text.substring(span.start, span.end.coerceAtMost(text.length)))
                    }
                }

                lastIndex = span.end.coerceAtMost(text.length)
            }
            append(text.substring(lastIndex))
        }
        return TransformedText(annotatedString, OffsetMapping.Identity)
    }
}