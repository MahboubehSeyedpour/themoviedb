package com.example.metapolitan.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.metapolitan.BuildConfig
import com.example.metapolitan.R
import com.example.metapolitan.data.remote.response.Movie
import com.example.metapolitan.presentation.theme.GunmetalGray

@Composable
fun MovieList(modifier: Modifier, movies: LazyPagingItems<Movie>, onMovieClicked: (Movie) -> Unit) {

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(
            start = 12.dp,
            top = 16.dp,
            end = 12.dp,
            bottom = 16.dp
        ),
        modifier = modifier,
    ) {
        items(movies.itemCount) { index ->
            val movie = movies[index]
            movie?.let {
                MovieCard(movie = movie,
                    onCardClicked = { onMovieClicked(movie)})
            }
        }

        movies.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    item { Text("Loading...") }
                }

                loadState.append is LoadState.Loading -> {
                    item { Text("Loading more...") }
                }

                loadState.refresh is LoadState.Error -> {
                    val e = loadState.refresh as LoadState.Error
                    item { Text("Error: ${e.error.localizedMessage}") }
                }

                loadState.append is LoadState.Error -> {
                    val e = loadState.append as LoadState.Error
                    item { Text("Error: ${e.error.localizedMessage}") }
                }
            }
        }
    }
}

@Composable
fun MovieCard(movie: Movie, onCardClicked: () -> Unit) {
    val rate = movie.voteAverage?.times(5)?.div(10)
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable { onCardClicked() },
        elevation = CardDefaults.elevatedCardElevation(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = GunmetalGray,
            contentColor = Color.White
        )
    ) {
        Column {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(BuildConfig.LARGE_IMAGE_URL + movie.backdropPath)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Column(
                modifier = Modifier.padding(8.dp)
            ) {
                Text(
                    text = movie.title ?: "",
                    style = TextStyle(fontWeight = FontWeight.SemiBold),
                    color = Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                movie.releaseDate?.let {
                    Text(
                        text = "(${movie.releaseDate.takeWhile { it != '-' }})",
                        color = Color.Gray,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                RatingStars(rating = rate?.toFloat() ?: 5f)
                Spacer(modifier = Modifier.height(8.dp))
                movie.overview?.let {
                    Text(
                        text = movie.overview,
                        color = Color.Gray,
                        minLines = 3,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}