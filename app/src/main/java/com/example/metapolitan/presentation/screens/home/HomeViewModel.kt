package com.example.metapolitan.presentation.screens.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.metapolitan.data.remote.PopularMoviesPagingSource
import com.example.metapolitan.data.remote.TopRatedMoviesPagingSource
import com.example.metapolitan.data.remote.UpcomingMoviesPagingSource
import com.example.metapolitan.data.remote.response.Movie
import com.example.metapolitan.domain.repository.MovieRepository
import com.google.gson.Gson
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

    private val popularMovieFlow = Pager(PagingConfig(pageSize = 20)) {
        PopularMoviesPagingSource(movieRepository)
    }.flow.cachedIn(viewModelScope)

    private val topRatedMovieFlow = Pager(PagingConfig(pageSize = 20)) {
        TopRatedMoviesPagingSource(movieRepository)
    }.flow.cachedIn(viewModelScope)

    private val upcomingMovieFlow = Pager(PagingConfig(pageSize = 20)) {
        UpcomingMoviesPagingSource(movieRepository)
    }.flow.cachedIn(viewModelScope)

    private var currentPager: Flow<PagingData<Movie>> = popularMovieFlow

    private val _moviesFlow = MutableStateFlow<PagingData<Movie>>(PagingData.empty())
    val moviesFlow: StateFlow<PagingData<Movie>> = _moviesFlow

    init {
        viewModelScope.launch {
            whenSelectedChipChanged()
        }
    }

    fun onSearchClicked() = viewModelScope.launch {
        _events.emit(HomeEvents.NavigateToSearchScreen)
    }

    fun onMovieClicked(movie: Movie) = viewModelScope.launch {
        _events.emit(
            HomeEvents.NavigateToMovieDetailsScreen(
                interScreenData = Gson().toJson(movie)
            )
        )
    }

    fun onChipSelected(chip: String) {
        _selectedChip.value = chip

        viewModelScope.launch {
            whenSelectedChipChanged()
        }
    }

    private suspend fun whenSelectedChipChanged() {
        _selectedChip.collect { chip ->
            currentPager = when (chip) {
                "Popular" -> popularMovieFlow
                "Top Rated" -> topRatedMovieFlow
                "Upcoming" -> upcomingMovieFlow
                else -> popularMovieFlow // Default to Popular if selectedChip is unexpected
            }
            currentPager.collect {
                _moviesFlow.value = it
            }
        }
    }
}