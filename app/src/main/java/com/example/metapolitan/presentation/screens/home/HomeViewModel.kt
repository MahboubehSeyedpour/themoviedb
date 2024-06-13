package com.example.metapolitan.presentation.screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.metapolitan.data.local.Movie
import com.example.metapolitan.data.remote.ModelState
import com.example.metapolitan.data.remote.Resource
import com.example.metapolitan.data.remote.response.MovieResponse
import com.example.metapolitan.domain.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(private val movieRepository: MovieRepository) : ViewModel() {

    private val _events = MutableSharedFlow<HomeEvents>()
    val events = _events.asSharedFlow()

    val chips = listOf("Popular", "Top Rated", "Upcoming")

    private val _selectedChip = MutableStateFlow("Popular")
    val selectedChip: StateFlow<String> = _selectedChip

    private val _items = MutableStateFlow<List<Movie>>(emptyList())
    val items: StateFlow<List<Movie>> = _items

    private val _isSearchBarVisible = MutableStateFlow(false)
    val isSearchBarVisible: StateFlow<Boolean> = _isSearchBarVisible

    fun toggleSearchBar() {
        _isSearchBarVisible.value = !_isSearchBarVisible.value
    }


    var startState by mutableStateOf(ModelState<MovieResponse>())
        private set

    init {
        fetchItems()
    }

    private fun fetchItems() = viewModelScope.launch(Dispatchers.IO){

        delay(4000)
        movieRepository.getPopularMovies().onEach { result ->
            when (result) {
                is Resource.Error -> {
                    startState = ModelState(error = result.message ?: "")
                }

                is Resource.Loading -> {
                    startState = ModelState(isLoading = true)
                }

                is Resource.Success -> {
                    startState = ModelState(response = result.data)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun onSearchClicked() = viewModelScope.launch {
        _events.emit(HomeEvents.avigateToSearchScreen)
    }

    fun onMovieClicked(movie: Movie) = viewModelScope.launch {
        _events.emit(HomeEvents.NavigateToMovieDetailsScreen)
    }

    fun onChipSelected(chip: String) {
        fetchItems()
        _selectedChip.value = chip
    }
}