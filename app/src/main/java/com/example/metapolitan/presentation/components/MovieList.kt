package com.example.metapolitan.presentation.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.metapolitan.data.local.Movie

@Composable
fun MovieList(modifier: Modifier, items: List<Movie>, onMovieClicked: (Movie) -> Unit) {

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),

        contentPadding = PaddingValues(
            start = 12.dp,
            top = 16.dp,
            end = 12.dp,
            bottom = 16.dp
        ),

        modifier = modifier,
        content = {
            items(items) { item ->
                MovieCard(movie = item, onCardClicked = { onMovieClicked(item) })
            }
        }
    )
}