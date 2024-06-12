package com.example.metapolitan.presentation.screens.home

sealed class HomeEvents {
    data object NavigateToMovieDetailsScreen : HomeEvents()
    data object avigateToSearchScreen : HomeEvents()
}