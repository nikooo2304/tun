package com.example.gromchetuner.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.gromchetuner.util.AudioAnalyzer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun TunerScreen() {
    val scope = rememberCoroutineScope()
    var pitch by remember { mutableStateOf(0f) }
    var note by remember { mutableStateOf("—") }

    LaunchedEffect(Unit) {
        scope.launch(Dispatchers.IO) {
            AudioAnalyzer.start { frequency, detectedNote ->
                pitch = frequency
                note = detectedNote
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Текущая нота:", style = MaterialTheme.typography.headlineSmall, color = MaterialTheme.colorScheme.primary)
        Text(text = note, style = MaterialTheme.typography.displayLarge, color = MaterialTheme.colorScheme.primary)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Частота: %.2f Гц".format(pitch), color = MaterialTheme.colorScheme.onBackground)
    }
}