package com.arcadone.cloudsharesnap.navigation

sealed class NavTarget(val route: String) {
    object Home : NavTarget("home")
    object SelectImages : NavTarget("image_selection")
    object PhotoResult : NavTarget("photo_result")
}