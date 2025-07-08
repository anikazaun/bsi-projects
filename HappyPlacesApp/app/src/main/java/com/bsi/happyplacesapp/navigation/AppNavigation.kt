package com.bsi.happyplacesapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            // TODO: HomeScreen()
        }
        composable("add_place") {
            // TODO: AddPlaceScreen()
        }
        composable("map") {
            // TODO: MapScreen()
        }
        composable("detail") {
            // TODO: DetailScreen()
        }
    }
}
