package com.arcadone.cloudsharesnap.presentation

import android.animation.ObjectAnimator
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import android.view.animation.DecelerateInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.arcadone.cloudsharesnap.navigation.NavTarget.*
import com.arcadone.cloudsharesnap.presentation.homescreen.HomeScreen
import com.arcadone.cloudsharesnap.presentation.loadphotoscreen.LoadPhotoScreen
import com.arcadone.cloudsharesnap.presentation.photoselectionscreen.PhotoSelectionScreen
import com.arcadone.cloudsharesnap.presentation.theme.CloudShareSnapTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()

        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()

            CloudShareSnapTheme {
                NavHost(navController, startDestination = Home.route) {
                    composable(Home.route) {
                        HomeScreen {
                            navController.navigate(SelectImages.route)
                        }
                    }
                    composable(SelectImages.route) {
                        PhotoSelectionScreen(navController, onClickLoadPhoto = { uriList ->
                            navController.navigate(
                                route =
                                "${PhotoResult.route}/${
                                    Uri.encode(uriList.joinToString(","))
                                }"
                            )
                        }
                        )
                    }
                    composable(route = "${PhotoResult.route}/{uriList}") { backStackEntry ->
                        val uriList = backStackEntry.arguments?.getString("uriList")
                            ?.split(",")?.map { Uri.parse(it) } ?: emptyList()
                        LoadPhotoScreen(navController, uriList)
                    }
                }
            }
        }

        setupSplashScreen(splashScreen)

    }

    private fun setupSplashScreen(splashScreen: SplashScreen) {
        val content: View = findViewById(android.R.id.content)
        content.viewTreeObserver.addOnPreDrawListener(
            object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    return run {
                        content.viewTreeObserver.removeOnPreDrawListener(this)
                        true
                    }
                }
            }
        )

        splashScreen.setOnExitAnimationListener { splashScreenView ->
            val slideBack = ObjectAnimator.ofFloat(
                splashScreenView.view,
                View.TRANSLATION_X,
                0f,
                -splashScreenView.view.width.toFloat()
            ).apply {
                interpolator = DecelerateInterpolator()
                duration = 100L
                doOnEnd { splashScreenView.remove() }
            }

            slideBack.start()
        }
    }
}
