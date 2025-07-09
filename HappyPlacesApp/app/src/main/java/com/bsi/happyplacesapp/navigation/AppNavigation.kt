package com.bsi.happyplacesapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.bsi.happyplacesapp.ui.screens.AddPlaceScreen
import com.bsi.happyplacesapp.ui.screens.DetailScreen
import com.bsi.happyplacesapp.ui.screens.HomeScreen
import com.bsi.happyplacesapp.ui.screens.MapScreen

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen()
        }
        composable("add_place") {
            AddPlaceScreen()
        }
        composable("map") {
            MapScreen()
        }
        composable("detail") {
            DetailScreen()
        }
    }
}
