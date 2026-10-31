package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
  primary = SomaliDarkPrimary,
  onPrimary = Color.Black,
  primaryContainer = SomaliBluePrimary,
  secondary = SomaliBrightGreen,
  onSecondary = Color.Black,
  tertiary = SomaliGold,
  background = SomaliDarkBg,
  surface = SomaliDarkCard,
  onBackground = SomaliDarkText,
  onSurface = SomaliDarkText,
  surfaceVariant = Color(0xFF1B2C45),
  onSurfaceVariant = SomaliDarkTextSubtle
)

private val LightColorScheme = lightColorScheme(
  primary = SomaliBluePrimary,
  onPrimary = Color.White,
  primaryContainer = Color(0xFFE3F2FD),
  secondary = SomaliSuccessGreen,
  onSecondary = Color.White,
  secondaryContainer = SomaliGreenContainer,
  tertiary = SomaliGold,
  background = SomaliSoftBg,
  surface = SomaliCardBg,
  onBackground = SomaliTextDark,
  onSurface = SomaliTextDark,
  surfaceVariant = Color(0xFFF0F4F8),
  onSurfaceVariant = SomaliTextSubtle,
  outline = SomaliBorder
)

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  dynamicColor: Boolean = false, // Keep brand aesthetic matching user mockup
  content: @Composable () -> Unit
) {
  val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

  MaterialTheme(
    colorScheme = colorScheme,
    typography = Typography,
    content = content
  )
}
