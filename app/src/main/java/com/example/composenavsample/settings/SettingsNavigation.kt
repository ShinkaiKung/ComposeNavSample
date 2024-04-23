package com.example.composenavsample.settings

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.composenavsample.Greeting
import com.example.composenavsample.OtherDestination

fun NavController.navigateToSettings() =
    navigate(OtherDestination.Settings.route)

fun NavGraphBuilder.settingsScreen() {
    composable(route = OtherDestination.Settings.route) {
        Greeting(name = "Settings")
    }
}
