package com.example.blackjack.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

import androidx.compose.runtime.getValue
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.vector.ImageVector

import androidx.compose.ui.graphics.Color
import com.example.blackjack.ui.views.Settings
import com.example.blackjack.ui.views.Start

@Composable
fun NavHost(
    navController: NavHostController,
    paddingValues: PaddingValues,
) {
    NavHost(
        navController = navController,
        startDestination = "start",
        modifier = Modifier.padding(paddingValues)
    ) {
        // Route: start
        composable("start") {
            Start()
        }

        // Route: settings
        composable("settings") {
            Settings()
        }
    }
}

data class BottomNavItem(
    val label: String,
    val icon: ImageVector,
    val route:String,
)

object Pages {
    val BottomNavItems = listOf(
        BottomNavItem(
            label = "Start",
            icon = Icons.Filled.Home,
            route = "start"
        ),
        BottomNavItem(
            label = "Settings",
            icon = Icons.Filled.Settings,
            route = "settings"
        )
    )
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {

    BottomAppBar(
        containerColor = Color(0xFF0F9D58),
        contentColor = Color.White
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        // observe current route to change the icon
        // color,label color when navigated
        val currentRoute = navBackStackEntry?.destination?.route
        // Iterate over the BottomNavItems we initiated earlier
        Pages.BottomNavItems.forEach { navItem ->
            NavigationBarItem(
                selected = currentRoute == navItem.route,
                onClick = {
                    navController.navigate(navItem.route)
                },
               icon = {
                    Icon(imageVector = navItem.icon, contentDescription = navItem.label)
               },
               label = {
                   Text(text = navItem.label)
               },
               alwaysShowLabel = false
            )
        }
    }
}
