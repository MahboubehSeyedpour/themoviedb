package com.example.metapolitan.presentation.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.metapolitan.R
import com.example.metapolitan.presentation.components.MaterialFilterChip
import com.example.metapolitan.presentation.components.MovieCard
import com.example.metapolitan.presentation.components.TopBar
import com.example.metapolitan.presentation.theme.BattleshipGray
import com.example.metapolitan.presentation.theme.DarkGray
import com.example.metapolitan.presentation.theme.Green
import com.example.metapolitan.presentation.theme.GunmetalGray
import com.example.metapolitan.presentation.theme.MetapolitanTheme

@Composable
fun HomeScreen(navController: NavController, viewModel: HomeViewModel = hiltViewModel()) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkGray)
    ) {
        Header(
            modifier = Modifier
                .weight(0.25f)
                .background(GunmetalGray)
                .padding(dimensionResource(id = R.dimen.padding)),
            viewModel = viewModel
        )

        Content(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.padding)),
            viewModel = viewModel
        )
    }
}

@Composable
fun Header(viewModel: HomeViewModel, modifier: Modifier) {

    val selectedChip by viewModel.selectedChip.collectAsState()

    Column(modifier = modifier, verticalArrangement = Arrangement.SpaceBetween) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(dimensionResource(id = R.dimen.padding)),
            verticalAlignment = Alignment.Bottom
        ) {

            TopBar(
                modifier = Modifier.fillMaxWidth(),
                title = "Watch Now",
                onSearchClick = { /* Handle search icon click */ },
                icon = R.drawable.ic_search
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(vertical = dimensionResource(id = R.dimen.padding))
                .horizontalScroll(rememberScrollState()),
            verticalAlignment = Alignment.Bottom
        ) {
            viewModel.chips.forEach { chip ->
                MaterialFilterChip(
                    modifier = Modifier.padding(
                        horizontal = dimensionResource(id = R.dimen.padding).div(2)
                    ),
                    label = {
                        Text(
                            text = chip,
                            style = TextStyle(
                                fontSize = 15.sp,
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                    },
                    colors = FilterChipDefaults.filterChipColors(
                        containerColor = BattleshipGray,
                        selectedContainerColor = Green,
                        labelColor = Color.White,
                        disabledLabelColor = Color.White,
                        selectedLabelColor = Color.White
                    ),
                    shape = RoundedCornerShape(45.dp),
                    selected = chip == selectedChip,
                    onClick = { viewModel.onChipSelected(chip) },
                    border = null
                )
            }
        }
    }
}

@Composable
fun Content(viewModel: HomeViewModel, modifier: Modifier) {
    val items by viewModel.items.collectAsState()

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier
    ) {
        items(items) { item ->
            MovieCard(movie = item)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MetapolitanTheme {
        HomeScreen(navController = rememberNavController())
    }
}