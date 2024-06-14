package com.example.metapolitan.presentation.screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.metapolitan.data.remote.ApiService
import com.example.metapolitan.data.remote.ModelState
import com.example.metapolitan.data.remote.MoviePagingSource
import com.example.metapolitan.data.remote.response.Movie
import com.example.metapolitan.data.remote.response.MovieResponse
import com.example.metapolitan.domain.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val _events = MutableSharedFlow<HomeEvents>()
    val events = _events.asSharedFlow()

    val chips = listOf("Popular", "Top Rated", "Upcoming")

    private val _selectedChip = MutableStateFlow("Popular")
    val selectedChip: StateFlow<String> = _selectedChip

    var startState by mutableStateOf(ModelState<MovieResponse>())
        private set
    val movieFlow = Pager(PagingConfig(pageSize = 20)) {
        MoviePagingSource(movieRepository)
    }.flow
        .cachedIn(viewModelScope)

    fun onSearchClicked() = viewModelScope.launch {
        _events.emit(HomeEvents.avigateToSearchScreen)
    }

    fun onMovieClicked() = viewModelScope.launch {
        _events.emit(HomeEvents.NavigateToMovieDetailsScreen)
    }

    fun onChipSelected(chip: String) {
        _selectedChip.value = chip
    }
}