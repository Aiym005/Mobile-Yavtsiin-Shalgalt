package com.example.mobileyavts.ui.item

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobileyavts.data.SettingsPreferencesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val settingsRepository: SettingsPreferencesRepository
) : ViewModel() {
    private val _selectedOption = MutableStateFlow(1)
    val selectedOption: StateFlow<Int> = _selectedOption.asStateFlow()
    init {
        observeSelectedOption()
    }
    private fun observeSelectedOption() {
        viewModelScope.launch {
            settingsRepository.getSavedSetting().collect { option ->
                _selectedOption.value = option
            }
        }
    }
    fun saveSettings(option: Int) {
        viewModelScope.launch {
            settingsRepository.saveSetting(option)
            _selectedOption.value = option
        }
    }
}
