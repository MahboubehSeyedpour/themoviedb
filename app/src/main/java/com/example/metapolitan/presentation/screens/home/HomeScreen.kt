package com.example.metapolitan.presentation.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.metapolitan.R
import com.example.metapolitan.presentation.components.FilterChips
import com.example.metapolitan.presentation.components.MovieList
import com.example.metapolitan.presentation.components.TopBar
import com.example.metapolitan.presentation.navigations.Screens
import com.example.metapolitan.presentation.theme.BattleshipGray
import com.example.metapolitan.presentation.theme.DarkGray
import com.example.metapolitan.presentation.theme.Green
import com.example.metapolitan.presentation.theme.GunmetalGray
import com.example.metapolitan.presentation.theme.MetapolitanTheme
import kotlinx.coroutines.flow.collectLatest


@Composable
fun HomeScreen(navController: NavController, viewModel: HomeViewModel = hiltViewModel()) {

    val lifecycle = LocalLifecycleOwner.current.lifecycle

    LaunchedEffect("events") {
        lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            viewModel.events.collectLatest { event ->
                when (event) {
                    HomeEvents.NavigateToMovieDetailsScreen -> navController.navigate(Screens.MovieDetailsScreen.route)
                    HomeEvents.avigateToSearchScreen -> navController.navigate(Screens.SearchScreen.route)
                }
            }
        }
    }

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

        MovieList(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize(),
            movies = viewModel.moviesFlow.collectAsLazyPagingItems(),
            onMovieClicked = { movie -> viewModel.onMovieClicked() }
        )
    }
}

@Composable
fun Header(viewModel: HomeViewModel, modifier: Modifier) {

    val selectedChip by viewModel.selectedChip.collectAsState()

    Column(modifier = modifier, verticalArrangement = Arrangement.SpaceBetween) {
        // Title and search icon
        TopBar(
            modifier = Modifier
                .fillMaxWidth()
                .weight(2f)
                .padding(dimensionResource(id = R.dimen.padding)),
            title = stringResource(id = R.string.watch_now),
            onSearchClick = { viewModel.onSearchClicked() },
            icon = Icons.Default.Search,
        )

        // filter chips
        Row(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize()
                .horizontalScroll(rememberScrollState())
        ) {
            viewModel.chips.forEach { chip ->
                FilterChips(
                    chipLabel = chip,
                    selected = chip == selectedChip,
                    onChipClicked = { chip -> viewModel.onChipSelected(chip) },
                    colors = FilterChipDefaults.filterChipColors(
                        containerColor = BattleshipGray,
                        selectedContainerColor = Green,
                        labelColor = Color.White,
                        disabledLabelColor = Color.White,
                        selectedLabelColor = Color.White
                    )
                )
            }
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