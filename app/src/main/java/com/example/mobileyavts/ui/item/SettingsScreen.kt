package com.example.mobileyavts.ui.item

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mobileyavts.R

@Composable
fun SettingsScreen(navController: NavController, viewModel: SettingsViewModel) {
    val selectedOption by viewModel.selectedOption.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = stringResource(R.string.settings),
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(top = 50.dp, bottom = 24.dp)
            )
            OptionButtonGroup(
                options = listOf(
                    1 to "Монгол үгийг харуулна",
                    2 to "Англи үгийг харуулна",
                    3 to "Хоёуланг нь харуулна"
                ),
                selectedOption = selectedOption,
                onOptionSelected = { viewModel.saveSettings(it) }
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(
                modifier = Modifier.weight(1f),
                onClick = { navController.popBackStack() }
            ) {
                Text(text = stringResource(R.string.cancel))
            }
            Button(
                modifier = Modifier.weight(1f),
                onClick = {
                    navController.popBackStack()
                }
            ) {
                Text(text = stringResource(R.string.save))
            }
        }
    }
}

@Composable
fun OptionButtonGroup(
    options: List<Pair<Int, String>>,
    selectedOption: Int,
    onOptionSelected: (Int) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        options.forEach { (value, label) ->
            val isSelected = value == selectedOption
            Button(
                onClick = { onOptionSelected(value) },
                modifier = Modifier.fillMaxWidth(),
                colors = if (isSelected)
                    ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                else
                    ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Text(
                    text = label,
                    color = if (isSelected)
                        MaterialTheme.colorScheme.onPrimary
                    else
                        MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Preview(showBackground = true, name = "Option Button Group Preview")
@Composable
fun PreviewOptionButtonGroup() {
    OptionButtonGroup(
        options = listOf(
            1 to "Монгол үгийг харуулна",
            2 to "Англи үгийг харуулна",
            3 to "Хоёуланг нь харуулна"
        ),
        selectedOption = 2,
        onOptionSelected = {}
    )
}