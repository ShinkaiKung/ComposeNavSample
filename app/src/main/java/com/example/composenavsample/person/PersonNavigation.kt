package com.example.composenavsample.person

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.composenavsample.Greeting
import com.example.composenavsample.TopLevelDestination

fun NavController.navigateToPerson(navOptions: NavOptions) =
    navigate(TopLevelDestination.Person.route, navOptions)

fun NavGraphBuilder.personScreen() {
    composable(route = TopLevelDestination.Person.route) {
        Greeting(name = "Person")
    }
}
