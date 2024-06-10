package com.example.metapolitan.presentation.navigations

sealed class Screens(val route: String) {
    data object HomeScreen : Screens("home")
    data object MovieDetailsScreen : Screens("details")
}