package com.hanif.passwordmanager.navigation

sealed class Screen(val route: String) {
    data object Home: Screen("home_screen")
}