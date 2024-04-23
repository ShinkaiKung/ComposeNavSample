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
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.get
import androidx.navigation.navOptions
import com.example.composenavsample.home.homeScreen
import com.example.composenavsample.home.navigateToHome
import com.example.composenavsample.login.hasLogin
import com.example.composenavsample.login.loginScreen
import com.example.composenavsample.person.navigateToPerson
import com.example.composenavsample.person.personScreen
import com.example.composenavsample.search.navigateToSearch
import com.example.composenavsample.search.searchScreen
import com.example.composenavsample.settings.navigateToSettings
import com.example.composenavsample.settings.settingsScreen
import com.example.composenavsample.shopping.navigateToShoppingCart
import com.example.composenavsample.shopping.shoppingCartScreen

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

enum class OtherDestination(val route: String) {
    Search(route = "Search"),
    Settings(route = "Settings"),
    Login(route = "Login"),
}

fun navigateToTopLevelDestination(
    navController: NavController,
    topLevelDestination: TopLevelDestination
) {
    val topLevelNavOptions = navOptions {
        // Pop up to the start destination of the graph to
        // avoid building up a large stack of destinations
        // on the back stack as users select items
        popUpTo(navController.graph.get(TopLevelDestination.Home.route).id) {
            saveState = true
        }
        // Avoid multiple copies of the same destination when
        // reselecting the same item
        launchSingleTop = true
        // Restore state when reselecting a previously selected item
        restoreState = true
    }

    when (topLevelDestination) {
        TopLevelDestination.Home -> navController.navigateToHome(topLevelNavOptions)
        TopLevelDestination.ShoppingCart -> navController.navigateToShoppingCart(topLevelNavOptions)
        TopLevelDestination.Person -> navController.navigateToPerson(topLevelNavOptions)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavLayout(navController: NavHostController) {
    Scaffold(
        modifier = Modifier,
        bottomBar = {
            if (navController.currentBackStackEntryAsState().value?.destination?.route in TopLevelDestination.entries.map { it.route }) {
                NavBottomBar(navController = navController)
            }
        }) { padding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            val currentDestination =
                navController.currentBackStackEntryAsState().value?.destination
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
                        IconButton(onClick = { navController.navigateToSearch() }) {
                            Icon(imageVector = Icons.Outlined.Search, contentDescription = null)
                        }
                    },
                    actions = {
                        IconButton(onClick = { navController.navigateToSettings() }) {
                            Icon(imageVector = Icons.Outlined.Settings, contentDescription = null)
                        }
                    }
                )
            }
            NavHost(
                navController = navController,
                startDestination = if (hasLogin) TopLevelDestination.Home.route else OtherDestination.Login.route
            ) {
                if (!hasLogin) {
                    loginScreen(navController)
                }
                homeScreen()
                shoppingCartScreen()
                personScreen()
                searchScreen()
                settingsScreen()
            }

        }

    }

}

@Composable
fun NavBottomBar(navController: NavHostController) {
    NavigationBar(modifier = Modifier) {
        val destinations = TopLevelDestination.entries
        destinations.forEach { destination ->
            val selected =
                navController.currentBackStackEntryAsState().value?.destination?.route == destination.route
            NavigationBarItem(
                selected = selected,
                onClick = { navigateToTopLevelDestination(navController, destination) },
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