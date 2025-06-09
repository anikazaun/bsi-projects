// Theme.kt

package com.bsi.quiz_app.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

// Deine Wunschfarbe in HEX – z. B. kräftiges Lila
val CustomPurple = Color(0xFF7B4B94)

private val DarkColorScheme = darkColorScheme(
    primary = CustomPurple,
    secondary = Color(0xFFCE93D8), // optional
    tertiary = Color(0xFFF48FB1)   // optional
)

private val LightColorScheme = lightColorScheme(
    primary = CustomPurple,
    secondary = Color(0xFFCE93D8),
    tertiary = Color(0xFFF48FB1)
)

@Composable
fun QuizAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // <-- GANZ WICHTIG!
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
