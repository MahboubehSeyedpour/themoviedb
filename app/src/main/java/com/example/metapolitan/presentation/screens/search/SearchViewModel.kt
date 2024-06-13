package com.example.metapolitan.presentation.screens.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.metapolitan.data.local.Movie
import com.example.metapolitan.presentation.screens.home.HomeEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor() : ViewModel() {

    private val _events = MutableSharedFlow<HomeEvents>()
    val events = _events.asSharedFlow()


    private val _items = MutableStateFlow<List<Movie>>(emptyList())
    val items: StateFlow<List<Movie>> = _items

    init {
        fetchItems()
    }

    private fun fetchItems() {
        viewModelScope.launch {
            _items.value = listOf(
                Movie(
                    id = "0",
                    title = "movie 0",
                    description = "description 0, description 0, description 0, description 0, description 0",
                    imageUrl = "image 0",
                    year = "2021",
                    rating = 1.0f
                ),
                Movie(
                    id = "1",
                    title = "movie 1",
                    description = "description 1",
                    imageUrl = "image 1",
                    year = "2024",
                    rating = 2.5f
                ),
                Movie(
                    id = "2",
                    title = "movie 2",
                    description = "description 2",
                    imageUrl = "image 2",
                    year = "2026",
                    rating = 4.5f
                ),
                Movie(
                    id = "3",
                    title = "movie 3",
                    description = "description 3",
                    imageUrl = "image 3",
                    year = "2023",
                    rating = 4.0f
                ),
                Movie(
                    id = "4",
                    title = "movie 4",
                    description = "description 4",
                    imageUrl = "image 4",
                    year = "2020",
                    rating = 3.7f
                ),
                Movie(
                    id = "5",
                    title = "movie 5",
                    description = "description 5",
                    imageUrl = "image 5",
                    year = "1998",
                    rating = 5.0f
                ),
                Movie(
                    id = "6",
                    title = "movie 6",
                    description = "description 6",
                    imageUrl = "image 6",
                    year = "2021",
                    rating = 5.0f
                ),
            )
        }
    }

    fun onMovieClicked(movie: Movie) = viewModelScope.launch {
        _events.emit(HomeEvents.NavigateToMovieDetailsScreen)
    }
}