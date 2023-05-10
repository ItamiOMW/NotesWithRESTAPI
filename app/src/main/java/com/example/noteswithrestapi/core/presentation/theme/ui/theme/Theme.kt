package com.example.noteswithrestapi.core.presentation.theme.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = primary_dark,
    secondary = secondary_dark,
    onSecondary = onSecondary_dark,
    tertiary = tertiary_dark,
    surface = surface_dark,
    onSurface = onSurface_dark,
    background = background_dark,
    onBackground = onBackground_dark,
)

private val LightColorScheme = lightColorScheme(
    primary = primary_light,
    secondary = secondary_light,
    onSecondary = onSecondary_light,
    tertiary = tertiary_light,
    surface = surface_light,
    onSurface = onSurface_light,
    background = background_light,
    onBackground = onBackground_light,
)

@Composable
fun NotesWithRESTAPITheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content,
        shapes = Shapes
    )
}