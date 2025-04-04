package com.example.mobileyavts.ui.item

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobileyavts.data.Word
import com.example.mobileyavts.data.WordsRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class FirstViewModel(
    private val wordsRepository: WordsRepository
) : ViewModel() {
    private val _words = MutableStateFlow<List<Word>>(emptyList())
    private val _currentIndex = MutableStateFlow(0)
    private val _currentWord = MutableStateFlow<Word?>(null)
    private val _isWordListEmpty = MutableStateFlow(true)
    val currentWord: StateFlow<Word?> = _currentWord.asStateFlow()
    val isWordListEmpty: StateFlow<Boolean> = _isWordListEmpty.asStateFlow()
    init {
        observeWords()
    }
    private fun observeWords() {
        viewModelScope.launch {
            wordsRepository.getAllWordsStream().collect { wordList ->
                _words.value = wordList
                _isWordListEmpty.value = wordList.isEmpty()
                _currentIndex.value = 0
                _currentWord.value = wordList.getOrNull(0)
            }
        }
    }
    fun deleteWord(id: Int) {
        viewModelScope.launch {
            val wordToDelete = wordsRepository.getWordStream(id).firstOrNull()
            wordToDelete?.let {
                wordsRepository.deleteWord(it)
                val updatedWords = _words.value
                if (updatedWords.isNotEmpty()) {
                    _currentIndex.value = 0
                    _currentWord.value = updatedWords.getOrNull(0)
                } else {
                    _currentWord.value = null
                }
            }
        }
    }
    fun nextWord() {
        val words = _words.value
        if (words.isNotEmpty()) {
            val nextIndex = (_currentIndex.value + 1) % words.size
            _currentIndex.value = nextIndex
            _currentWord.value = words[nextIndex]
        }
    }
    fun prevWord() {
        val words = _words.value
        if (words.isNotEmpty()) {
            val prevIndex = if (_currentIndex.value > 0) _currentIndex.value - 1 else words.size - 1
            _currentIndex.value = prevIndex
            _currentWord.value = words[prevIndex]
        }
    }
}
