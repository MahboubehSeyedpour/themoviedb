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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.FilterChipDefaults
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
import com.example.metapolitan.presentation.components.MaterialFilterChip
import com.example.metapolitan.presentation.components.RatingStars
import com.example.metapolitan.presentation.theme.BattleshipGray
import com.example.metapolitan.presentation.theme.DarkGray
import com.example.metapolitan.presentation.theme.MetapolitanTheme

@Composable
fun MovieDetailsScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkGray)
    ) {
        TopPart(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.33f)
                .padding(vertical = dimensionResource(id = R.dimen.padding))
        )
        BottomPart(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        )
    }
}

@Composable
fun TopPart(modifier: Modifier) {
    Box(
        modifier = modifier
    ) {
        // Background image
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground), // Replace with your image resource
            contentDescription = "Background Image",
            modifier = Modifier
                .fillMaxSize()
                .aspectRatio(16f / 9f) // Maintain 16:9 aspect ratio
        )

        // Left icon
        IconButton(
            onClick = { /* Your action for left icon */ },
            modifier = Modifier.align(Alignment.TopStart)
        ) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowLeft, // Replace with your desired icon
                contentDescription = "Left Icon",
                tint = Color.White,
                modifier = Modifier.size(dimensionResource(id = R.dimen.icon_size))
            )
        }

        // Right icon
        IconButton(
            onClick = { /* Your action for right icon */ },
            modifier = Modifier.align(Alignment.TopEnd)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_bookmark_fill), // Replace with your desired icon
                contentDescription = "Right Icon",
                tint = Color.White,
                modifier = Modifier.size(dimensionResource(id = R.dimen.icon_size))
            )
        }
    }
}

@Composable
fun BottomPart(modifier: Modifier) {

    Column(
        modifier = modifier
    ) {
        // Movie title and year
        Text(
            text = "Movie Title (2024)", // Replace with actual title and year
            style = MaterialTheme.typography.titleMedium,
            color = Color.White,
            fontWeight = FontWeight.SemiBold,
            fontSize = 28.sp
        )

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding)))

        Row(
            modifier = Modifier.fillMaxWidth(), // Limit width for score
            horizontalArrangement = Arrangement.Start
        ) {
            RatingStars(rating = 4.2F)
            Spacer(modifier = Modifier.size(4.dp)) // Add spacing between star and score
            Text(text = "4.2", color = Color.White)
        }

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding)))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
        ) {
            listOf("Action", "Adventure", "Comedy").forEach { genre ->
                MaterialFilterChip(
                    label = { Text(text = genre) },
                    enabled = false,
                    modifier = Modifier.padding(end = dimensionResource(id = R.dimen.padding)),
                    onClick = {},
                    selected = false,
                    colors = FilterChipDefaults.filterChipColors(
                        disabledContainerColor = BattleshipGray,
                        labelColor = Color.White,
                        disabledLabelColor = Color.White,
                        selectedLabelColor = Color.White
                    ),
                    shape = RoundedCornerShape(45.dp),
                )
            }
        }

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding)))

        Text(text = "Storyline", color = Color.White, fontSize = 22.sp) // Semi-transparent text

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding).div(2)))

        LazyColumn(
            modifier = Modifier
                .padding(vertical = dimensionResource(id = R.dimen.padding))
                .fillMaxWidth()
                .weight(1f) // Fill remaining space and weight for scrolling
        ) {
            item {
                Text(
                    text = stringResource(id = R.string.lorem),
                    color = Color.White,
                    fontSize = 16.sp,
                    lineHeight = 20.sp // Adjust line spacing for readability
                )
            }
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