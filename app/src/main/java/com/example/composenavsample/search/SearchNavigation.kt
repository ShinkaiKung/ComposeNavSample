package com.example.composenavsample.search

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.composenavsample.Greeting
import com.example.composenavsample.OtherDestination

fun NavGraphBuilder.searchScreen() {
    composable(route = OtherDestination.Search.route) {
        Greeting(name = "Search")
    }
}

fun NavController.navigateToSearch() =
    navigate(OtherDestination.Search.route)
