package com.example.composenavsample.home

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.composenavsample.Greeting
import com.example.composenavsample.TopLevelDestination

fun NavController.navigateToHome(navOptions: NavOptions) =
    navigate(TopLevelDestination.Home.route, navOptions)

fun NavGraphBuilder.homeScreen() {
    composable(route = TopLevelDestination.Home.route) {
        Greeting(name = "Home")
    }
}
