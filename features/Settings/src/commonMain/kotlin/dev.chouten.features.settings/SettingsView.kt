package dev.chouten.features.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.inumaki.core.ui.components.AppButton
import com.inumaki.core.ui.model.AppConfig
import com.inumaki.core.ui.theme.AppTheme

@Composable
fun SettingsView(appConfig: AppConfig) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Settings", style = AppTheme.typography.title3, fontWeight = FontWeight.Bold, color = AppTheme.colors.fg)

            AppButton("drawable/xmark-solid-full.svg", angle = 0f, background = AppTheme.colors.overlay, onClick = { appConfig.navController.popBackStack() })
        }
    }
}
