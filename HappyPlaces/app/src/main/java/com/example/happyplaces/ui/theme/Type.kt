package com.example.happyplaces.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp

val Typography = Typography(
    headlineLarge = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 30.sp,
        letterSpacing = 0.sp,
        fontFamily = FontFamily.Default
    ),
    headlineSmall = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 22.sp,
        letterSpacing = 0.sp,
        fontFamily = FontFamily.Default
    ),
    titleLarge = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp,
        letterSpacing = 0.sp,
        fontFamily = FontFamily.Default
    ),
    bodyLarge = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        letterSpacing = 0.sp,
        fontFamily = FontFamily.Default
    ),
    bodyMedium = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        letterSpacing = 0.sp,
        fontFamily = FontFamily.Default
    ),
    labelLarge = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp
    )
)
