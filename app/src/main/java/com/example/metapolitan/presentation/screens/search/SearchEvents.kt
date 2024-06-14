package com.example.metapolitan.presentation.screens.search

sealed class SearchEvents {
    data class NavigateToMovieDetailsScreen(val interScreenData: String) : SearchEvents()
}