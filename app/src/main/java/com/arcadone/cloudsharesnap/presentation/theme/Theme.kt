package com.arcadone.cloudsharesnap.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.arcadone.cloudsharesnap.R

@Immutable
data class CustomColors(
    val color01: Color,
    val color02: Color,
    val color03: Color,
    val color04: Color,
    val color05: Color,
    val color06: Color,
    val color07: Color,
    val color08: Color,
    val color09: Color,
    val color10: Color,
    val color11: Color,
    val color12: Color,
    val color13: Color,
    val color14: Color,
)

@Immutable
data class CustomTypography(
    val text01: TextStyle,
    val text02: TextStyle,
    val text03Bold: TextStyle,
    val text04: TextStyle,
    val text05Bold: TextStyle,
)

val LocalCustomColors = staticCompositionLocalOf {
    CustomColors(
        color01 = Color.Unspecified,
        color02 = Color.Unspecified,
        color03 = Color.Unspecified,
        color04 = Color.Unspecified,
        color05 = Color.Unspecified,
        color06 = Color.Unspecified,
        color07 = Color.Unspecified,
        color08 = Color.Unspecified,
        color09 = Color.Unspecified,
        color10 = Color.Unspecified,
        color11 = Color.Unspecified,
        color12 = Color.Unspecified,
        color13 = Color.Unspecified,
        color14 = Color.Unspecified,
    )
}
val LocalCustomTypography = staticCompositionLocalOf {
    CustomTypography(
        text01 = TextStyle.Default,
        text02 = TextStyle.Default,
        text03Bold = TextStyle.Default,
        text04 = TextStyle.Default,
        text05Bold = TextStyle.Default,
    )
}

val fonts = FontFamily(
    Font(R.font.karla_regular, weight = FontWeight.W400, style = FontStyle.Normal),
    Font(R.font.karla_bold, weight = FontWeight.W700, style = FontStyle.Normal)
)

val Typography = CustomTypography(
    text01 = TextStyle(fontFamily = fonts, fontSize = 22.sp, fontWeight = FontWeight.Bold),
    text02 = TextStyle(fontFamily = fonts, fontSize = 17.sp, lineHeight = 22.sp, fontWeight = FontWeight.Medium),
    text03Bold = TextStyle(fontFamily = fonts, fontSize = 28.sp, fontWeight = FontWeight.Bold),
    text04 = TextStyle(fontFamily = fonts, fontSize = 14.sp, lineHeight = 19.sp, fontWeight = FontWeight.Medium),
    text05Bold = TextStyle(fontFamily = fonts, fontSize = 24.sp, fontWeight = FontWeight.Bold),
)

@Composable
fun CloudShareSnapTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalCustomColors provides if (!useDarkTheme) {
            LightColors
        } else {
            DarkColors
        },
        LocalCustomTypography provides Typography,
    ) {
        MaterialTheme(
            content = content
        )
    }
}

object CloudShareSnap {
    val colors: CustomColors
        @Composable
        get() = LocalCustomColors.current
    val typography: CustomTypography
        @Composable
        get() = LocalCustomTypography.current
}
