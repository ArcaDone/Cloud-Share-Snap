package com.arcadone.cloudsharesnap.compose

import android.content.res.Configuration
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.arcadone.cloudsharesnap.presentation.theme.CloudShareSnap
import com.arcadone.cloudsharesnap.presentation.theme.CloudShareSnapTheme
import kotlinx.coroutines.delay

@Composable
fun ThreeBounceAnimation() {
    val dots = listOf(
        remember { Animatable(0.2f) },
        remember { Animatable(0.2f) },
        remember { Animatable(0.2f) },
    )

    dots.forEachIndexed { index, animatable ->
        LaunchedEffect(animatable) {
            delay(index * 200L)
            animatable.animateTo(
                targetValue = 1f, animationSpec = infiniteRepeatable(
                    animation = tween(600, easing = FastOutLinearInEasing),
                    repeatMode = RepeatMode.Reverse,
                )
            )
        }
    }

    val dys = dots.map { it.value }

    Row(
        modifier = Modifier.fillMaxSize().background(CloudShareSnap.colors.color01),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        dys.forEachIndexed { _, dy ->
            Box(
                Modifier
                    .size(30.dp)
                    .scale(dy)
                    .alpha(dy)
                    .background(color = Color.White, shape = CircleShape)
            )

        }
    }
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
)
@Composable
private fun DotsPreview() {

    CloudShareSnapTheme {
        ThreeBounceAnimation()
    }
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
private fun DotsDarkPreview() {

    CloudShareSnapTheme {
        ThreeBounceAnimation()
    }
}
