package com.example.noteswithrestapi.core.presentation.theme.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.noteswithrestapi.R

// Set of Material typography styles to start with
val ud_digi_family = FontFamily(listOf(Font(R.font.ud_digi_kyokasho_nk_b, weight = FontWeight.Bold)))

val roboto_family = FontFamily(listOf(
    Font(R.font.roboto_regular, weight = FontWeight.Normal),
    Font(R.font.roboto_bold, weight = FontWeight.Bold),
    Font(R.font.roboto_medium, weight = FontWeight.Medium),
    Font(R.font.roboto_light, weight = FontWeight.Light)
))

val Typography = Typography(
    headlineMedium = TextStyle(
        fontFamily = ud_digi_family,
        fontWeight = FontWeight.Bold,
        lineHeight = 28.sp,
        fontSize = 28.sp
    ),
    titleLarge = TextStyle(
        fontFamily = ud_digi_family,
        fontWeight = FontWeight.Bold,
        letterSpacing = 0.5.sp,
        lineHeight = 28.sp,
        fontSize = 22.sp
    ),
    titleMedium = TextStyle(
        fontFamily = ud_digi_family,
        fontWeight = FontWeight.Bold,
        letterSpacing = 0.5.sp,
        lineHeight = 28.sp,
        fontSize = 20.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = roboto_family,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.5.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = roboto_family,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.4.sp
    ),
    bodySmall = TextStyle(
        fontFamily = roboto_family,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 28.sp,
    )
)