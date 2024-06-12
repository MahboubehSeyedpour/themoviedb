package com.example.metapolitan.presentation.screens.home

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.metapolitan.R
import com.example.metapolitan.presentation.components.MaterialFilterChip
import com.example.metapolitan.presentation.components.MovieCard
import com.example.metapolitan.presentation.navigations.Screens
import com.example.metapolitan.presentation.screens.home.components.TopBar
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

        Content(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize(),
            viewModel = viewModel
        )
    }
}

@Composable
fun Header(viewModel: HomeViewModel, modifier: Modifier) {

    Column(modifier = modifier, verticalArrangement = Arrangement.SpaceBetween) {

        TopBar(
            modifier = Modifier
                .fillMaxWidth()
                .weight(2f)
                .padding(dimensionResource(id = R.dimen.padding)),
            title = stringResource(id = R.string.watch_now),
            onSearchClick = { viewModel.onSearchClicked() },
            icon = Icons.Default.Search,
        )

        FilterChips(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
                .horizontalScroll(rememberScrollState()),
            contentVerticalAlignment = Alignment.Bottom,
            viewModel = viewModel
        )
    }
}

@Composable
fun FilterChips(
    modifier: Modifier,
    contentVerticalAlignment: Alignment.Vertical,
    viewModel: HomeViewModel
) {
    val selectedChip by viewModel.selectedChip.collectAsState()

    Row(
        modifier = modifier,
        verticalAlignment = contentVerticalAlignment
    ) {
        viewModel.chips.forEach { chip ->
            MaterialFilterChip(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .padding(dimensionResource(id = R.dimen.padding).div(2)),
                label = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
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

@Composable
fun Content(viewModel: HomeViewModel, modifier: Modifier) {
    val items by viewModel.items.collectAsState()

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),

        // content padding
        contentPadding = PaddingValues(
            start = 12.dp,
            top = 16.dp,
            end = 12.dp,
            bottom = 16.dp
        ),

        modifier = modifier,
        content = {
            items(items) { item ->
                MovieCard(movie = item, onCardClicked = { viewModel.onMovieClicked() })
            }
        }
    )
}


@Composable
fun SearchBar(modifier: Modifier, viewModel: HomeViewModel) {
    val isSearchBarVisible by viewModel.isSearchBarVisible.collectAsState()

    Box(
        modifier = Modifier.focusModifier(),
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .animateContentSize(
                    animationSpec = tween(
                        durationMillis = 300,
                        easing = LinearOutSlowInEasing
                    )
                )
                .clip(RoundedCornerShape(6.dp))
                .padding(horizontal = 16.dp, vertical = 8.dp),
            color = GunmetalGray
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (isSearchBarVisible) {
                    BasicTextField(
                        value = "",
                        onValueChange = {},
                        modifier = Modifier
                            .weight(1f)
                            .padding(8.dp)
                            .background(Color.White, shape = MaterialTheme.shapes.small)
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        singleLine = true
                    )
                }
                IconButton(
                    onClick = { viewModel.toggleSearchBar() },
                    modifier = Modifier
                        .padding(8.dp)
                ) {
                    Icon(
                        modifier = Modifier.size(dimensionResource(id = R.dimen.icon_size)),
                        imageVector = if (isSearchBarVisible) Icons.Default.Close else Icons.Default.Search,
                        contentDescription = "Search",
                        tint = Color.White
                    )
                }
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