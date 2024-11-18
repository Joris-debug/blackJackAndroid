package com.example.blackjack.ui.views

import android.content.pm.PackageManager
import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat

@Composable
fun Settings(modifier: Modifier = Modifier) {
    val permissionGranted = remember { mutableStateOf(false) }

    LazyColumn(
        modifier = modifier.padding(16.dp)
    ) {
        item {
            Text(
                text = "Welcome to the settings page! Please allow access to the system files.",
                modifier = modifier
            )
        }

        item {
            val launcher = rememberLauncherForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted: Boolean ->
                permissionGranted.value = isGranted
            }

            val context = LocalContext.current

            Button(
                onClick = {
                    when (ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.POST_NOTIFICATIONS
                    )) {
                        PackageManager.PERMISSION_GRANTED -> {
                            permissionGranted.value = true
                        }
                        else -> {
                            launcher.launch(Manifest.permission.POST_NOTIFICATIONS)
                        }
                    }
                }
            ) {
                Text(text = "Check and Request Permission")
            }
        }

        item {
            if (permissionGranted.value) {
                Text(text = "Permission granted! You can access the files.")
            } else {
                Text(text = "Permission denied or not granted yet.")
            }
        }
    }
}
