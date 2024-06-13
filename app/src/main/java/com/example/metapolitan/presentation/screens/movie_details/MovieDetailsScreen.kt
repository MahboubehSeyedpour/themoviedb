package com.example.metapolitan.presentation.screens.movie_details

import androidx.compose.foundation.Image
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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.metapolitan.R
import com.example.metapolitan.presentation.components.FilterChips
import com.example.metapolitan.presentation.components.RatingStars
import com.example.metapolitan.presentation.theme.DarkGray
import com.example.metapolitan.presentation.theme.MetapolitanTheme

@Composable
fun MovieDetailsScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkGray)
            .padding(vertical = dimensionResource(id = R.dimen.padding))
    ) {

        MoviePoster(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.33f)
        )

        // -------- Movie title and year --------
        Text(
            text = "Movie Title (2024)",
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
            RatingStars(rating = 4.2F)
            Spacer(modifier = Modifier.size(4.dp))
            Text(text = "4.2", color = Color.White)
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
//                    modifier = Modifier.padding(end = dimensionResource(id = R.dimen.padding)),
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
                    text = stringResource(id = R.string.lorem),
                    color = Color.White,
                    fontSize = 16.sp,
                    lineHeight = 20.sp
                )
            }
        }
    }
}

@Composable
fun MoviePoster(modifier: Modifier) {
    Box(
        modifier = modifier
    ) {
        // Background image
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = "poster",
            modifier = Modifier
                .fillMaxSize()
                .aspectRatio(16f / 9f)
        )

        // bookmark icon
        IconButton(
            onClick = { },
            modifier = Modifier.align(Alignment.TopStart)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                contentDescription = "Bookmark",
                tint = Color.White,
                modifier = Modifier.size(dimensionResource(id = R.dimen.icon_size))
            )
        }

        // Back icon
        IconButton(
            onClick = {  },
            modifier = Modifier.align(Alignment.TopEnd)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_bookmark_fill),
                contentDescription = "Back",
                tint = Color.White,
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