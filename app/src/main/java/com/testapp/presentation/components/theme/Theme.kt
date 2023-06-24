package com.testapp.presentation.components.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.testapp.R

@Composable
fun AppMainTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {

    val typography = Typography(
        //Large Title
        //56
        h1 = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.SemiBold,
            fontSize = dimensionResource(id = R.dimen._28ssp).value.sp,
            lineHeight = dimensionResource(id = R.dimen._28ssp).value.sp,
            letterSpacing = 0.sp
        ),
        //Large Title
        //32px
        h2 = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.SemiBold,
            fontSize = dimensionResource(id = R.dimen._22ssp).value.sp,
            lineHeight = dimensionResource(id = R.dimen._22ssp).value.sp,
            letterSpacing = 0.sp
        ),
        //Large Body
        //16px
        body1 = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Normal,
            fontSize = dimensionResource(id = R.dimen._12ssp).value.sp,
            lineHeight = dimensionResource(id = R.dimen._12ssp).value.sp,
            letterSpacing = 0.5.sp
        ),
        //labelSmall
        //12px
        caption = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Normal,
            fontSize = dimensionResource(id = R.dimen._11ssp).value.sp,
            lineHeight = dimensionResource(id = R.dimen._11ssp).value.sp,
            letterSpacing = 0.5.sp
        )
    )

    val colorScheme = when {
        darkTheme -> DarkColorPalette
        else -> LightColorPalette
    }

    MaterialTheme(
        colors = colorScheme,
        typography = typography,
        content = content
    )
}