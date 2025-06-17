package com.example.visualtransformationtextinput

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.component.VisualTransformationTextInput
import com.example.visualtransformationtextinput.ui.theme.VisualTransformationTextInputTheme
import com.example.visualtransformationtextinput.uiEvent.MainActivityUIEvent
import com.example.visualtransformationtextinput.viewModel.MainActivityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val vm: MainActivityViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VisualTransformationTextInputTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val state by vm.state.collectAsStateWithLifecycle()
                    Greeting(
                        state = state,
                        modifier = Modifier.padding(innerPadding),
                        onUIEvent = vm::onUIEvent
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(
    state: MainActivityViewModel.MainActivityState,
    modifier: Modifier = Modifier,
    onUIEvent: (MainActivityUIEvent) -> Unit,
) {
    Box(
        modifier = modifier
    ) {
        VisualTransformationTextInput(
            textFieldValue = state.textFieldValue,
            taggedText = state.taggedText,
            onValueChanged = {
                onUIEvent(MainActivityUIEvent.OnTextChanged(it))
            },
            color = Color.Red
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    VisualTransformationTextInputTheme {
        Greeting(MainActivityViewModel.MainActivityState()) {}
    }
}