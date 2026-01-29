package com.inumaki.features.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.inumaki.core.ui.theme.AppTheme
import dev.chouten.runners.relay.RelayLogger

@Composable
fun HomeView() {
    BoxWithConstraints(
        modifier = Modifier.fillMaxSize()
    ) {
        val width = maxWidth
        val height = maxHeight

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Home")
            Text("Width: $width")
            Text("Height: $height")

            Text("Logs:", style = AppTheme.typography.title3)
            RelayLogger.logs.forEach {
                Text(it)
            }
        }
    }
}
