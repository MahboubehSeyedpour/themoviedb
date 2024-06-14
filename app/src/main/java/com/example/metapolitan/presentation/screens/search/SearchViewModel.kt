package com.example.metapolitan.presentation.screens.search

import androidx.lifecycle.ViewModel
import com.example.metapolitan.presentation.screens.home.HomeEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor() : ViewModel() {

    private val _events = MutableSharedFlow<HomeEvents>()
    val events = _events.asSharedFlow()

//    fun onMovieClicked(movie: Movie) = viewModelScope.launch {
//        _events.emit(HomeEvents.NavigateToMovieDetailsScreen)
//    }
}