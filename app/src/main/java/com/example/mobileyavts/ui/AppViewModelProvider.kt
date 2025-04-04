package com.example.mobileyavts.ui

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.mobileyavts.InventoryApplication
import com.example.mobileyavts.ui.item.FirstViewModel
import com.example.mobileyavts.ui.item.SettingsViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            val application = this.inventoryApplication()
            val repository= application.container.itemsRepository
            FirstViewModel(repository)
        }
        initializer {
            val application = this.inventoryApplication()
            val repository = application.container.settingsPreferencesRepository
            SettingsViewModel(repository)
        }
    }
}
fun CreationExtras.inventoryApplication(): InventoryApplication =
    this[AndroidViewModelFactory.APPLICATION_KEY] as InventoryApplication