package com.example.mobileyavts.ui.item

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobileyavts.data.Word
import com.example.mobileyavts.data.WordsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class WordEditViewModel(
    private val wordId: Int?,
    private val wordsRepository: WordsRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(EditUiState())
    val uiState: StateFlow<EditUiState> = _uiState.asStateFlow()
    private val isEditMode = wordId != null && wordId != -1
    init {
        if (isEditMode) {
            loadWordForEdit(wordId!!)
        }
    }
    private fun loadWordForEdit(id: Int) {
        viewModelScope.launch {
            wordsRepository.getWordStream(id)
                .filterNotNull()
                .collectLatest { word ->
                    _uiState.value = EditUiState(
                        id = word.id,
                        mongolUg = word.mongolUg,
                        angliUg = word.angliUg,
                        isEntryValid = true
                    )
                }
        }
    }
    fun updateUiState(mongolUg: String, angliUg: String) {
        _uiState.value = _uiState.value.copy(
            mongolUg = mongolUg,
            angliUg = angliUg,
            isEntryValid = mongolUg.isNotBlank() && angliUg.isNotBlank()
        )
    }
    fun saveWord() {
        if (!uiState.value.isEntryValid) return
        val word = Word(
            id = if (isEditMode) wordId!! else 0,
            mongolUg = uiState.value.mongolUg,
            angliUg = uiState.value.angliUg
        )
        viewModelScope.launch {
            if (isEditMode) {
                wordsRepository.updateWord(word)
            } else {
                wordsRepository.insertWord(word)
            }
        }
    }
}

data class EditUiState(
    val id: Int = 0,
    val mongolUg: String = "",
    val angliUg: String = "",
    val isEntryValid: Boolean = false
)
