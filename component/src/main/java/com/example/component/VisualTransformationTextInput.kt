package com.example.component

import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import com.example.component.models.TaggedTextModel

@Composable
fun VisualTransformationTextInput(
    textFieldValue: TextFieldValue,
    taggedText: TaggedTextModel,
    onValueChanged: (TextFieldValue) -> Unit,
    color: Color
) {
    val focusRequester = remember { FocusRequester() }

    TextField(
        modifier = Modifier
            .focusRequester(focusRequester),
        value = textFieldValue,
        onValueChange = {
            onValueChanged(it)
        },
        visualTransformation = KeywordVisualTransformation(
            taggedText = taggedText,
            color = color
        ),
        colors = TextFieldDefaults.colors().copy(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White
        )
    )
}