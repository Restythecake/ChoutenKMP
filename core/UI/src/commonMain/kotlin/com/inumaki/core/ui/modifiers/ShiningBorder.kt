package com.inumaki.core.ui.modifiers

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun Modifier.shiningBorder(
    angle: Float,
    radius: Dp
): Modifier {
    return this
        .clip(RoundedCornerShape(radius))
        .drawWithContent {
            rotate(angle) {
                drawCircle(
                    brush = Brush.sweepGradient(
                        colors = listOf(
                            Color(0xff3b3b3b),
                            Color(0xff17171A),
                            Color(0xff17171A),
                            Color(0xff17171A),
                            Color(0xff3b3b3b),
                            Color(0xff3b3b3b),
                            Color(0xff17171A),
                            Color(0xff17171A),
                            Color(0xff17171A),
                            Color(0xff17171A),
                            Color(0xff17171A),
                            Color(0xff3b3b3b)
                        )
                    ),
                    radius = size.width
                )
            }

            drawContent()
        }
        .padding(0.5.dp)
}
