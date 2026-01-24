package com.inumaki.features.discover.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.inumaki.core.ui.components.AppButton
import com.inumaki.core.ui.modifiers.shiningBorder
import com.inumaki.core.ui.model.PosterData

@Composable
fun CarouselCard(data: PosterData, angle: Float) {
    Box(
        modifier = Modifier
            .padding(40.dp)
            .fillMaxWidth()
            .aspectRatio(0.65f)
            .shiningBorder(angle, 22.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(Color(0xff171717)),
        contentAlignment = Alignment.TopEnd
    ) {
        AsyncImage(
            model = data.poster,
            contentDescription = null,
            contentScale = ContentScale.FillHeight,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(0.65f)
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.linearGradient(
                        listOf(
                            Color(0x0017171A),
                            Color(0xff17171A)
                        )
                    )
                )
        )

        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(14.dp)
        ) {
            Text(
                data.title.secondary ?: "",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 18.sp,
                maxLines = 2,
                modifier = Modifier.alpha(0.7f)
            )

            Text(
                data.title.primary,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 22.sp,
                maxLines = 2,
                modifier = Modifier.padding(bottom = 6.dp)
            )

            Text(
                data.description ?: "",
                fontSize = 12.sp,
                lineHeight = 14.sp,
                maxLines = 3,
                fontWeight = FontWeight.Medium,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.alpha(0.7f)
            )
        }

        AppButton("drawable/plus-solid-full.svg", angle, modifier = Modifier.padding(12.dp))
    }
}