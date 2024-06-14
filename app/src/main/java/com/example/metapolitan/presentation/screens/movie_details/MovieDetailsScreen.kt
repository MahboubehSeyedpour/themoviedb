package com.example.metapolitan.presentation.screens.movie_details

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.metapolitan.BuildConfig
import com.example.metapolitan.R
import com.example.metapolitan.presentation.components.FilterChips
import com.example.metapolitan.presentation.components.RatingStars
import com.example.metapolitan.presentation.theme.DarkGray
import com.example.metapolitan.presentation.theme.MetapolitanTheme

@Composable
fun MovieDetailsScreen(
    navController: NavController,
    viewModel: MovieDetailsViewModel = hiltViewModel()
) {

    val rate = viewModel.movie.voteAverage?.times(5)?.div(10)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkGray)
            .padding(dimensionResource(id = R.dimen.padding))
    ) {

        MoviePoster(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.33f),
            poster = viewModel.movie.backdropPath ?: ""
        )

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding)))

        // -------- Movie title and year --------
        Text(
            text = viewModel.movie.title ?: "",
            style = MaterialTheme.typography.titleMedium,
            color = Color.White,
            fontWeight = FontWeight.SemiBold,
            fontSize = 28.sp
        )

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding)))


        // ---------- Stars and rating ----------
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            RatingStars(rating = rate?.toFloat() ?: 5F)
            Spacer(modifier = Modifier.size(4.dp))
            Text(text = rate.toString().take(3), color = Color.White)
        }

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding)))

        // --------- Genres -----------
        Row(
            modifier = Modifier
                .weight(0.15f)
                .fillMaxSize()
                .horizontalScroll(rememberScrollState())
        ) {
            listOf("Action", "Adventure", "Comedy").forEach { genre ->
                FilterChips(
                    chipLabel = genre,
                    selected = false,
                    onChipClicked = {}
                )
            }
        }

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding)))

        Text(text = "Storyline", color = Color.White, fontSize = 22.sp)

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding).div(2)))


        // -------- Movie storyline ----------
        LazyColumn(
            modifier = Modifier
                .padding(vertical = dimensionResource(id = R.dimen.padding))
                .fillMaxWidth()
                .weight(1f)
        ) {
            item {
                Text(
                    text = viewModel.movie.overview ?: "",
                    color = Color.White,
                    fontSize = 18.sp,
                    lineHeight = 30.sp
                )
            }
        }
    }
}

@Composable
fun MoviePoster(modifier: Modifier, poster: String) {
    val context = LocalContext.current
    Box(
        modifier = modifier
    ) {

        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(BuildConfig.LARGE_IMAGE_URL + poster)
                .crossfade(true)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
        )

        // bookmark icon
        IconButton(
            onClick = { },
            modifier = Modifier.align(Alignment.TopStart)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                contentDescription = "Bookmark",
                tint = DarkGray,
                modifier = Modifier.size(dimensionResource(id = R.dimen.icon_size))
            )
        }

        // Back icon
        IconButton(
            onClick = { },
            modifier = Modifier.align(Alignment.TopEnd)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_bookmark_fill),
                contentDescription = "Back",
                tint = DarkGray,
                modifier = Modifier.size(dimensionResource(id = R.dimen.icon_size))
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MetapolitanTheme {
        MovieDetailsScreen(navController = rememberNavController())
    }
}