package com.example.visualtransformationtextinput.utils

import com.example.component.models.Span
import com.example.component.models.TaggedTextModel


object TaggedTextUtil {

    /**
     * Change TaggedTextModel
     * @param editedText edited text
     * @param original original tagged text
     */
    fun changeTaggedText(editedText: String, original: TaggedTextModel): TaggedTextModel {
        val newSpans = mutableListOf<Span>()
        val sb = StringBuilder()

        var lastIndex = 0

        val changedSymbolIndex = findChangedCharIndex(original.plainText, editedText)
        val changedSymbolsCount = editedText.length - original.plainText.length

        // Handle all spans from original
        for (span in original.spans) {
            // If changes was closed to a keyword then we need to remove a highlight
            if (changedSymbolIndex == span.start || changedSymbolIndex == span.end) {
                continue
            }
            if (span.end <= (changedSymbolIndex ?: 0)) {
                newSpans.add(span)
                continue
            }
            // Calculate new indexes for span in accordance with changed text
            val newStart = (span.start + changedSymbolsCount).coerceAtMost(editedText.length)
            val newEnd = (span.end + changedSymbolsCount).coerceAtMost(editedText.length)

            // Add the text before current word
            sb.append(editedText.substring(lastIndex, newStart))

            if (newStart >= editedText.length) break

            val newWord = editedText.substring(newStart, newEnd)

            // Original word for current span
            val originalWord = original.plainText.substring(span.start, span.end)

            // If a word does not changed, add highlight
            if (newWord == originalWord) {
                newSpans.add(Span(sb.length, sb.length + newWord.length, KEYWORD_TAG))
            }

            // Add the new word to result text
            sb.append(newWord)
            lastIndex = newEnd
        }

        // Add other text after last word in tags
        sb.append(editedText.substring(lastIndex))

        return TaggedTextModel(sb.toString(), newSpans)
    }

    /**
     * Find index of changed char
     * @param originalText original text
     * @param editedText edited text
     */
    private fun findChangedCharIndex(originalText: String, editedText: String): Int? {
        // If the length of the text is greater, it means that symbols were inserted
        if (originalText.length < editedText.length) {
            // Search first index where text changed
            for (i in originalText.indices) {
                if (originalText[i] != editedText[i]) {
                    return i
                }
            }
            // if the difference is at the end, then symbols were inserted at the end
            return originalText.length
        }

        // If the length of the text is less, it means that symbols were deleted
        if (originalText.length > editedText.length) {
            // Search first index where text changed
            for (i in editedText.indices) {
                if (originalText[i] != editedText[i]) {
                    // Возвращаем индекс удалённого символа
                    // Return index of deleted symbol
                    return i
                }
            }
            // If the difference is at the end, then the last symbol was deleted
            return originalText.length - 1
        }

        // If the length of the text did not change, it means nothing was inserted or deleted
        return null
    }
}