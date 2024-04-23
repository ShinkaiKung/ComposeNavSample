package com.example.composenavsample.shopping

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.composenavsample.Greeting
import com.example.composenavsample.TopLevelDestination

fun NavController.navigateToShoppingCart(navOptions: NavOptions) =
    navigate(TopLevelDestination.ShoppingCart.route, navOptions)

fun NavGraphBuilder.shoppingCartScreen() {
    composable(route = TopLevelDestination.ShoppingCart.route) {
        Greeting(name = "ShoppingCart")
    }
}
