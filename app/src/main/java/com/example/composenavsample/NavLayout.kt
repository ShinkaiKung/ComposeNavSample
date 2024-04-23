package com.example.composenavsample

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navOptions

enum class TopLevelDestination(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val iconTextId: String,
    val titleTextId: String,
    val route: String
) {
    Home(
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home,
        iconTextId = "Home",
        titleTextId = "Home",
        route = "Home",
    ),
    ShoppingCart(
        selectedIcon = Icons.Filled.ShoppingCart,
        unselectedIcon = Icons.Outlined.ShoppingCart,
        iconTextId = "ShoppingCart",
        titleTextId = "ShoppingCart",
        route = "ShoppingCart",
    ),
    Person(
        selectedIcon = Icons.Filled.Person,
        unselectedIcon = Icons.Outlined.Person,
        iconTextId = "Person",
        titleTextId = "Person",
        route = "Person",
    ),
}

fun NavController.navigateToHome(navOptions: NavOptions) =
    navigate(TopLevelDestination.Home.route, navOptions)

fun NavController.navigateToShoppingCart(navOptions: NavOptions) =
    navigate(TopLevelDestination.ShoppingCart.route, navOptions)

fun NavController.navigateToPerson(navOptions: NavOptions) =
    navigate(TopLevelDestination.Person.route, navOptions)

fun NavGraphBuilder.homeScreen() {
    composable(route = TopLevelDestination.Home.route) {
        Greeting(name = "Home")
    }
}

fun NavGraphBuilder.shoppingCartScreen() {
    composable(route = TopLevelDestination.ShoppingCart.route) {
        Greeting(name = "ShoppingCart")
    }
}

fun NavGraphBuilder.personScreen() {
    composable(route = TopLevelDestination.Person.route) {
        Greeting(name = "Person")
    }
}

fun navigateToTopLevelDestination(
    naviController: NavHostController,
    topLevelDestination: TopLevelDestination
) {
    val topLevelNavOptions = navOptions {
        // Pop up to the start destination of the graph to
        // avoid building up a large stack of destinations
        // on the back stack as users select items
        popUpTo(naviController.graph.findStartDestination().id) {
            saveState = true
        }
        // Avoid multiple copies of the same destination when
        // reselecting the same item
        launchSingleTop = true
        // Restore state when reselecting a previously selected item
        restoreState = true
    }

    when (topLevelDestination) {
        TopLevelDestination.Home -> naviController.navigateToHome(topLevelNavOptions)
        TopLevelDestination.ShoppingCart -> naviController.navigateToShoppingCart(topLevelNavOptions)
        TopLevelDestination.Person -> naviController.navigateToPerson(topLevelNavOptions)
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun naviLayout(naviController: NavHostController) {
    Scaffold(
        modifier = Modifier,
        bottomBar = { naviBottomBar(naviController = naviController) }) { padding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            val currentDestination =
                naviController.currentBackStackEntryAsState().value?.destination
            val currentTopLevelDestination = when (currentDestination?.route) {
                TopLevelDestination.Home.route -> TopLevelDestination.Home
                TopLevelDestination.ShoppingCart.route -> TopLevelDestination.ShoppingCart
                TopLevelDestination.Person.route -> TopLevelDestination.Person
                else -> null
            }
            if (currentTopLevelDestination != null) {
                CenterAlignedTopAppBar(
                    title = { Text(text = currentTopLevelDestination.titleTextId) },
                    navigationIcon = {
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(imageVector = Icons.Outlined.Search, contentDescription = null)
                        }
                    },
                    actions = {
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(imageVector = Icons.Outlined.Settings, contentDescription = null)
                        }
                    }
                )
            }
            NavHost(
                navController = naviController,
                startDestination = TopLevelDestination.Home.route
            ) {
                homeScreen()
                shoppingCartScreen()
                personScreen()
            }

        }

    }

}

@Composable
//@Preview
fun naviBottomBar(naviController: NavHostController) {
    NavigationBar(modifier = Modifier) {
        val destinations = TopLevelDestination.entries
        destinations.forEach { destination ->
            val selected =
                naviController.currentBackStackEntryAsState().value?.destination?.route == destination.route
            NavigationBarItem(
                selected = selected,
                onClick = { navigateToTopLevelDestination(naviController, destination) },
                icon = {
                    Icon(
                        imageVector = if (selected) destination.selectedIcon else destination.unselectedIcon,
                        contentDescription = null
                    )
                },
                label = { Text(text = destination.iconTextId) }
            )

        }
    }
}