package com.example.visualtransformationtextinput.viewModel

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.component.models.TaggedTextModel
import com.example.visualtransformationtextinput.uiEvent.MainActivityUIEvent
import com.example.visualtransformationtextinput.mapper.TaggedTextMapper
import com.example.visualtransformationtextinput.utils.TaggedTextUtil.changeTaggedText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainActivityViewModel(
    private val mapper: TaggedTextMapper,
) : ViewModel() {

    private val text = "<keyword>Jack London</keyword> was born on January 12, 1876. By age 30, he was internationally famous for <keyword>Call of the Wild (1903)</keyword>, <keyword>The Sea Wolf (1904)</keyword> and many other literary and journalistic accomplishments."

    private val _state = MutableStateFlow(MainActivityState())
    val state: StateFlow<MainActivityState> = _state

    init {
        viewModelScope.launch(Dispatchers.Default) {
            val taggedText = mapper.map(text)
            _state.value = MainActivityState(
                taggedText = taggedText,
                textFieldValue = TextFieldValue(taggedText.plainText)
            )
        }
    }

    fun onUIEvent(event: MainActivityUIEvent) {
        when (event) {
            is MainActivityUIEvent.OnTextChanged -> {
                val newTaggedText = changeTaggedText(event.value.text, state.value.taggedText)
                _state.update {
                    it.copy(
                        taggedText = newTaggedText,
                        textFieldValue = event.value.copy(text = newTaggedText.plainText)
                    )
                }
            }
        }
    }

    data class MainActivityState(
        val taggedText: TaggedTextModel = TaggedTextModel(),
        val textFieldValue: TextFieldValue = TextFieldValue()
    )
}