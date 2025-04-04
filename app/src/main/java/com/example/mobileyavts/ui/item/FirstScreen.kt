package com.example.mobileyavts.ui.item

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mobileyavts.R
import com.example.mobileyavts.data.Word


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FirstScreen(navController: NavController, viewModel: FirstViewModel, settingsViewModel: SettingsViewModel) {
    var spacing by remember { mutableStateOf(70.dp) }
    val configuration = LocalConfiguration.current
    val isWordListEmpty by viewModel.isWordListEmpty.collectAsState()
    val selectedOption by settingsViewModel.selectedOption.collectAsState()
    val currentWord by viewModel.currentWord.collectAsState()
    var isDialogVisible by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(configuration.orientation) {
        spacing =
            if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) 10.dp else 70.dp
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.app_name),
                        modifier = Modifier.padding(start = dimensionResource(R.dimen.padding_medium)),
                        color = colorScheme.onPrimary,
                        style = MaterialTheme.typography.titleLarge,
                    )
                },
                actions = {
                    IconButton(
                        onClick = { navController.navigate("SettingsScreen") },
                        modifier = Modifier.padding(end = dimensionResource(R.dimen.padding_medium))
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Share,
                            contentDescription = stringResource(R.string.settings),
                            tint = colorScheme.onPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorScheme.primary,
                    titleContentColor = colorScheme.onPrimary,
                    actionIconContentColor = colorScheme.onPrimary
                ),
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    ) { paddingValues ->
        if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.Top
            ) {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.Start
                ) {
                    item { Spacer(modifier = Modifier.height(spacing * 2)) }

                    item {
                        if (isWordListEmpty) {
                            Spacer(modifier = Modifier.height(spacing * 2))
                        } else {
                            when (selectedOption) {
                                1 -> WordCard(null, currentWord, DisplayMode.MONGOL)
                                2 -> WordCard(null, currentWord, DisplayMode.ENGLISH)
                                3 -> WordCard(navController, currentWord, DisplayMode.BOTH)
                            }
                        }
                    }
                    item { Spacer(modifier = Modifier.height(spacing * 2)) }
                }
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.End
                ) {
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Button(
                            modifier = Modifier.weight(1f),
                            onClick = { navController.navigate("WordEditScreen/${-1}") }
                        ) {
                            Text(text = stringResource(R.string.add), fontSize = 11.sp)
                        }
                        Button(
                            modifier = Modifier.weight(1f),
                            onClick = { navController.navigate("WordEditScreen/${currentWord?.id}") },
                            enabled = !isWordListEmpty
                        ) {
                            Text(text = stringResource(R.string.update), fontSize = 11.sp)
                        }
                        Button(
                            modifier = Modifier.weight(1f),
                            onClick = { isDialogVisible = true },
                            enabled = !isWordListEmpty
                        ) {
                            Text(text = stringResource(R.string.delete), fontSize = 11.sp)
                        }
                    }
                    Spacer(modifier = Modifier.height(spacing))
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Button(
                            modifier = Modifier.weight(1f),
                            onClick = { viewModel.prevWord() },
                            enabled = !isWordListEmpty
                        ) {
                            Text(text = stringResource(R.string.before), fontSize = 11.sp)
                        }
                        Button(
                            modifier = Modifier.weight(1f),
                            onClick = { viewModel.nextWord() },
                            enabled = !isWordListEmpty
                        ) {
                            Text(text = stringResource(R.string.after), fontSize = 11.sp)
                        }
                    }
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item { Spacer(modifier = Modifier.height(spacing * 2)) }

                item {
                    if (isWordListEmpty) {
                        Spacer(modifier = Modifier.height(spacing * 2))
                    } else {
                        when (selectedOption) {
                            1 -> WordCard(null, currentWord, DisplayMode.MONGOL)
                            2 -> WordCard(null, currentWord, DisplayMode.ENGLISH)
                            3 -> WordCard(navController, currentWord, DisplayMode.BOTH)
                        }
                    }
                }
                item { Spacer(modifier = Modifier.height(spacing * 2)) }
                item {
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Button(
                            modifier = Modifier.weight(1f),
                            onClick = { navController.navigate("WordEditScreen/${-1}") }
                        ) {
                            Text(text = stringResource(R.string.add), fontSize = 11.sp)
                        }
                        Button(
                            modifier = Modifier.weight(1f),
                            onClick = { navController.navigate("WordEditScreen/${currentWord?.id}") },
                            enabled = !isWordListEmpty
                        ) {
                            Text(text = stringResource(R.string.update), fontSize = 11.sp)
                        }
                        Button(
                            modifier = Modifier.weight(1f),
                            onClick = { isDialogVisible = true },
                            enabled = !isWordListEmpty
                        ) {
                            Text(text = stringResource(R.string.delete), fontSize = 11.sp)
                        }
                    }
                }
                item { Spacer(modifier = Modifier.height(spacing)) }
                item {
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Button(
                            modifier = Modifier.weight(1f),
                            onClick = { viewModel.prevWord() },
                            enabled = !isWordListEmpty
                        ) {
                            Text(text = stringResource(R.string.before), fontSize = 11.sp)
                        }
                        Button(
                            modifier = Modifier.weight(1f),
                            onClick = { viewModel.nextWord() },
                            enabled = !isWordListEmpty
                        ) {
                            Text(text = stringResource(R.string.after), fontSize = 11.sp)
                        }
                    }
                }
            }
        }
    }
    if (isDialogVisible) {
        AlertDialog(
            onDismissRequest = { isDialogVisible = false },
            title = { Text(text = stringResource(R.string.delete)) },
            text = { Text(text = stringResource(R.string.sure)) },
            confirmButton = {
                Button(
                    onClick = {
                        currentWord?.id?.let { viewModel.deleteWord(it) }
                        isDialogVisible = false
                    }
                ) {
                    Text( text = stringResource(R.string.yes))
                }
            },
            dismissButton = {
                Button(onClick = { isDialogVisible = false }) {
                    Text( text = stringResource(R.string.no))
                }
            }
        )
    }
}

enum class DisplayMode {
    MONGOL,
    ENGLISH,
    BOTH
}

@Composable
fun WordCard(
    navController: NavController?,
    currentWord: Word?,
    mode: DisplayMode
) {
    var isTextVisible by remember { mutableStateOf(false) }
    val (primaryText, secondaryText) = when (mode) {
        DisplayMode.MONGOL -> currentWord?.mongolUg to currentWord?.angliUg
        DisplayMode.ENGLISH -> currentWord?.angliUg to currentWord?.mongolUg
        DisplayMode.BOTH -> currentWord?.mongolUg to currentWord?.angliUg
    }
    Text(
        text = primaryText ?: "",
        fontSize = 40.sp,
        style = MaterialTheme.typography.bodyLarge,
        modifier = Modifier
            .padding(16.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        if (mode != DisplayMode.BOTH) {
                            isTextVisible = !isTextVisible
                        }
                    },
                    onLongPress = {
                        if (mode == DisplayMode.BOTH) {
                            currentWord?.id?.let {
                                navController?.navigate("WordEditScreen/$it")
                            }
                        }
                    }
                )
            }
    )
    Spacer(modifier = Modifier.height(50.dp))
    if (mode == DisplayMode.BOTH) {
        Text(
            text = secondaryText ?: "",
            fontSize = 40.sp,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .padding(16.dp)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onLongPress = {
                            currentWord?.id?.let {
                                navController?.navigate("WordEditScreen/$it")
                            }
                        }
                    )
                }
        )
    } else {
        Box(
            modifier = Modifier
                .pointerInput(Unit) {
                    detectTapGestures(
                        onTap = {
                            isTextVisible = !isTextVisible
                        }
                    )
                }
                .padding(16.dp)
        ) {
            Text(
                text = if (isTextVisible) secondaryText ?: "" else "Дарна уу",
                fontSize = 40.sp,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}
