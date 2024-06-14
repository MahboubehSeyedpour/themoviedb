package com.example.metapolitan.presentation.screens.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.metapolitan.data.remote.SearchResultPagingSource
import com.example.metapolitan.data.remote.response.Movie
import com.example.metapolitan.domain.repository.MovieRepository
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val _events = MutableSharedFlow<SearchEvents>()
    val events = _events.asSharedFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private var currentQuery: String = ""

    private val _searchResultFlow = MutableStateFlow<PagingData<Movie>>(PagingData.empty())
    val searchResultFlow: StateFlow<PagingData<Movie>> = _searchResultFlow

    fun onSearchQueryChanged(value: String) {
        _searchQuery.value = value
    }

    fun onSearchButtonClicked() {
        currentQuery = _searchQuery.value
        fetchSearchResult(currentQuery)
    }

    private fun fetchSearchResult(query: String) {
        viewModelScope.launch {
            Pager(PagingConfig(pageSize = 20)) {
                SearchResultPagingSource(movieRepository, query)
            }.flow.cachedIn(viewModelScope).collectLatest {
                _searchResultFlow.value = it
            }
        }
    }

    fun onMovieClicked(movie: Movie) = viewModelScope.launch {
        _events.emit(
            SearchEvents.NavigateToMovieDetailsScreen(
                interScreenData = Gson().toJson(movie)
            )
        )
    }

    fun onQueryClear() {
        _searchQuery.value = ""
    }
}