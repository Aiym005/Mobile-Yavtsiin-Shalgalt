package com.example.mobileyavts.ui.item

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.mobileyavts.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun WordEditScreen(
    navController: NavController,
    viewModel: FakeWordEditViewModel
) {
    val uiState by viewModel.uiState.collectAsState()
    Column(
        modifier = Modifier
            .statusBarsPadding()
            .padding(horizontal = 40.dp)
            .verticalScroll(rememberScrollState())
            .safeDrawingPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.mongolian),
            modifier = Modifier
                .padding(bottom = 16.dp, top = 40.dp)
                .align(Alignment.Start)
        )
        OutlinedTextField(
            value = uiState.mongolUg,
            singleLine = true,
            shape = shapes.large,
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = colorScheme.surface,
                unfocusedContainerColor = colorScheme.surface,
                disabledContainerColor = colorScheme.surface,
            ),
            onValueChange = { viewModel.updateUiState(it, uiState.angliUg)},
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            placeholder = {
                Text(text = stringResource(R.string.textfield))
            }
        )
        Text(
            text = stringResource(R.string.english),
            modifier = Modifier
                .padding(bottom = 16.dp, top = 40.dp)
                .align(Alignment.Start)
        )
        OutlinedTextField(
            value = uiState.angliUg,
            singleLine = true,
            shape = shapes.large,
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = colorScheme.surface,
                unfocusedContainerColor = colorScheme.surface,
                disabledContainerColor = colorScheme.surface,
            ),
            onValueChange = { viewModel.updateUiState(uiState.mongolUg, it)},
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            placeholder = {
                Text(text = stringResource(R.string.textfield))
            }
        )
        Spacer(modifier = Modifier.height(150.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(
                modifier = Modifier.weight(1f),
                onClick = { navController.popBackStack() }
            ) {
                Text(text = stringResource(R.string.cancel), fontSize = 11.sp)
            }
            Button(
                modifier = Modifier.weight(1f),
                onClick = {
                    if (uiState.isEntryValid) {
                        viewModel.saveWord()
                        navController.popBackStack()
                    }
                },
                enabled = uiState.isEntryValid,
            ) {
                Text(text = stringResource(R.string.save), fontSize = 11.sp)
            }
        }
    }
}



data class WordUiState(
    val mongolUg: String = "",
    val angliUg: String = "",
    val isEntryValid: Boolean = false
)

class FakeWordEditViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(
        WordUiState(
            mongolUg = "Нохой",
            angliUg = "Dog",
            isEntryValid = true
        )
    )
    val uiState: StateFlow<WordUiState> = _uiState

    fun updateUiState(mongol: String, english: String) {
        _uiState.value = _uiState.value.copy(
            mongolUg = mongol,
            angliUg = english,
            isEntryValid = mongol.isNotBlank() && english.isNotBlank()
        )
    }

    fun saveWord() {
    }
}

@Preview(showBackground = true, name = "Word Edit Screen Preview")
@Composable
fun PreviewWordEditScreen() {
    val navController = rememberNavController()
    val fakeViewModel = FakeWordEditViewModel()

    WordEditScreen(navController = navController, viewModel = fakeViewModel)
}
