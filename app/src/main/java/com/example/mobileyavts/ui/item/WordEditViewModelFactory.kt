package com.example.mobileyavts.ui.item

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mobileyavts.data.WordsRepository

class WordEditViewModelFactory(
    private val wordId: Int?,
    private val repository: WordsRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(WordEditViewModel::class.java) -> {
                WordEditViewModel(wordId, repository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}
