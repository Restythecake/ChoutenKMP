package com.inumaki.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.inumaki.core.ui.model.TopBarConfig


@Composable
fun AppTopBar(topBarConfig: TopBarConfig?, angle: Float) {
    topBarConfig?.let { config ->
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.linearGradient(
                        0.0f to Color(0xff0c0c0c),
                        1.0f to Color(0x000c0c0c),
                        start = Offset(0f, 0f),
                        end = Offset(0f, 200f)
                    )
                )
                .padding(start = 24.dp, top = 40.dp, end = 24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(config.title, fontSize = 18.sp, fontWeight = FontWeight.Bold)

            config.actions.forEach { action ->
                AppButton("drawable/${action.icon}", angle)
            }
        }
    }
}