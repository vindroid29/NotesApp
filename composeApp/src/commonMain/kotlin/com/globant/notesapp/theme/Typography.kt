package com.globant.notesapp.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import notesapp.composeapp.generated.resources.Res
import notesapp.composeapp.generated.resources.nunito_medium
import notesapp.composeapp.generated.resources.nunito_regular
import notesapp.composeapp.generated.resources.nunito_semi_bold
import org.jetbrains.compose.resources.Font


val Nunito
    @Composable get() = FontFamily(
        Font(
            resource = Res.font.nunito_semi_bold,
            weight = FontWeight.SemiBold
        ),
        Font(
            resource = Res.font.nunito_medium,
            weight = FontWeight.Medium
        ),
        Font(
            resource = Res.font.nunito_regular,
            weight = FontWeight.Normal
        )
    )
val Typography @Composable get() = Typography(
    titleLarge = TextStyle(
        fontFamily = Nunito,
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = Nunito,
        fontSize = 18.sp,
        fontWeight = FontWeight.Medium
    ),
    bodyMedium = TextStyle(
        fontFamily = Nunito,
        fontSize = 16.sp,
        fontWeight = FontWeight.Medium,
        lineHeight = 24.sp,
    ),
    bodySmall = TextStyle(
        fontFamily = Nunito,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 20.sp
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)