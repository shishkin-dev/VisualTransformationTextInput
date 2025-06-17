package com.example.visualtransformationtextinput.uiEvent

import androidx.compose.ui.text.input.TextFieldValue

sealed interface MainActivityUIEvent {

    data class OnTextChanged(val value: TextFieldValue) : MainActivityUIEvent
}