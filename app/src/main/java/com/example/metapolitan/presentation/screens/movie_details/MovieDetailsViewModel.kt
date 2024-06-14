package com.example.metapolitan.presentation.screens.movie_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.metapolitan.data.remote.response.Movie
import com.example.metapolitan.presentation.screens.home.HomeEvents
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
): ViewModel() {

    // get received data from previous screen
    var movie: Movie = Gson().fromJson(
        savedStateHandle.get<String>("interScreenData")!!, Movie::class.java
    )

    private val _events = MutableSharedFlow<HomeEvents>()
    val events = _events.asSharedFlow()


}