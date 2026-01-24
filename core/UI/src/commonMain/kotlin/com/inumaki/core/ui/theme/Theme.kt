package com.inumaki.core.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Define color scheme
data class AppColors(
    val background: Color,
    val container: Color,
    val overlay: Color,
    val border: Color,
    val fg: Color,
    val accent: Color
)

val LightAppColors = AppColors(
    background = Color(0xFFF5F5F5),
    container = Color(0xFFFFFFFF),
    overlay = Color(0xFF212121),
    border = Color(0xFFE0E0E0),
    fg = Color(0xFF757575),
    accent = Color(0xFFFF4081)
)

val DarkAppColors = AppColors(
    background = Color(0xFF0C0C0C),
    container = Color(0xFF171717),
    overlay = Color(0xFF272727),
    border = Color(0xFF3B3B3B),
    fg = Color(0xFFD4D4D4),
    accent = Color(0xFF5E5CE6)
)

// Define typography
data class AppTypography(
    val title: TextStyle,
    val body: TextStyle,
    val caption: TextStyle
)

val DefaultTypography = AppTypography(
    title = TextStyle(
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = FontFamily.Default
    ),
    body = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal,
        fontFamily = FontFamily.Default
    ),
    caption = TextStyle(
        fontSize = 12.sp,
        fontWeight = FontWeight.Light,
        fontFamily = FontFamily.Default
    )
)

// Define shapes
data class AppShapes(
    val small: RoundedCornerShape,
    val medium: RoundedCornerShape,
    val large: RoundedCornerShape
)

val DefaultShapes = AppShapes(
    small = RoundedCornerShape(4.dp),
    medium = RoundedCornerShape(8.dp),
    large = RoundedCornerShape(16.dp)
)

// Define CompositionLocals
val LocalChoutenColors = staticCompositionLocalOf { LightAppColors }
val LocalChoutenTypography = staticCompositionLocalOf { DefaultTypography }
val LocalChoutenShapes = staticCompositionLocalOf { DefaultShapes }

// ChoutenTheme provider function
@Composable
fun AppTheme(
    colors: AppColors = if (isSystemInDarkTheme()) DarkAppColors else LightAppColors,
    typography: AppTypography = DefaultTypography,
    shapes: AppShapes = DefaultShapes,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalChoutenColors provides colors,
        LocalChoutenTypography provides typography,
        LocalChoutenShapes provides shapes,
        content = content
    )
}

object AppTheme {
    val colors: AppColors
        @Composable get() = LocalChoutenColors.current

    val typography: AppTypography
        @Composable get() = LocalChoutenTypography.current

    val shapes: AppShapes
        @Composable get() = LocalChoutenShapes.current
}