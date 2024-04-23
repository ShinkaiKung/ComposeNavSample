package com.example.composenavsample.login

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.composenavsample.OtherDestination
import com.example.composenavsample.TopLevelDestination
import com.example.composenavsample.navigateToTopLevelDestination

var hasLogin = false

fun NavController.navigateToLogin() =
    navigate(OtherDestination.Login.route)

fun NavGraphBuilder.loginScreen(navController: NavController) {
    composable(route = OtherDestination.Login.route) {
        Button(onClick = {
            hasLogin = true
            navigateToTopLevelDestination(
                navController,
                TopLevelDestination.Home
            )
        }) {
            Text(text = "Log in")
        }
    }
}
