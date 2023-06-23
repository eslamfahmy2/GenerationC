package com.testapp.presentation.components.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController

@Composable
fun MainAppGraph(modifier: Modifier = Modifier) {

    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Destinations.HomeScreen.route,
        modifier = modifier
    ) {
        homeDestination(navController)
        carDetailsDestination(navController)
    }
}