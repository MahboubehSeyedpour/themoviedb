package com.example.metapolitan.presentation.screens.home

sealed class HomeEvents {
    data class NavigateToMovieDetailsScreen(val interScreenData: String) : HomeEvents()
    data object NavigateToSearchScreen : HomeEvents()
}