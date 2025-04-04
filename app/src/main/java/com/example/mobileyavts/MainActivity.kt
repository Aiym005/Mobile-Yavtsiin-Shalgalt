package com.example.mobileyavts

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.example.mobileyavts.ui.theme.InventoryTheme
import com.example.mobileyavts.workers.cancelScheduledNotification
import com.example.mobileyavts.workers.scheduleNotification

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            InventoryTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    InventoryApp()
                }
            }
        }
        observeLifecycleForNotifications()
    }
    private fun observeLifecycleForNotifications() {
        val notificationTitle = intent.getStringExtra("Title") ?: "Үгээ мартав!"
        val lifecycleObserver = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_STOP,
                Lifecycle.Event.ON_PAUSE,
                Lifecycle.Event.ON_DESTROY -> {
                    scheduleNotification(this, notificationTitle)
                }
                Lifecycle.Event.ON_RESUME -> {
                    cancelScheduledNotification(this)
                }
                else -> Unit
            }
        }
        lifecycle.addObserver(lifecycleObserver)
    }
}
