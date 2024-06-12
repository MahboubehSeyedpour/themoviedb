package com.example.metapolitan.presentation.screens.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.dimensionResource
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
import com.example.metapolitan.presentation.components.MovieCard
import com.example.metapolitan.presentation.navigations.Screens
import com.example.metapolitan.presentation.screens.home.HomeEvents
import com.example.metapolitan.presentation.theme.DarkGray
import com.example.metapolitan.presentation.theme.GunmetalGray
import com.example.metapolitan.presentation.theme.MetapolitanTheme
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SearchScreen(navController: NavController, viewModel: SearchViewModel = hiltViewModel()) {


//    val lifecycle = LocalLifecycleOwner.current.lifecycle
//
//    LaunchedEffect("events") {
//        lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
//            viewModel.events.collectLatest { event ->
//                when (event) {
//                    Sea.NavigateToMovieDetailsScreen -> navController.navigate(Screens.MovieDetailsScreen.route)
//                    HomeEvents.avigateToSearchScreen -> navController.navigate(Screens.SearchScreen.route)
//                }
//            }
//        }
//    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkGray)
    ) {
        Header(
            modifier = Modifier
                .weight(0.25f)
                .fillMaxWidth()
                .background(GunmetalGray)
                .padding(dimensionResource(id = R.dimen.padding))
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
fun Header(modifier: Modifier) {


    Column(modifier = modifier) {
        ScreenTitle(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.padding)),
            title = "Search",
            trailingIcon = Icons.Outlined.KeyboardArrowLeft
        )
        SearchBar(
            Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = dimensionResource(id = R.dimen.padding))
        )
    }
}

@Composable
fun ScreenTitle(modifier: Modifier, title: String, trailingIcon: ImageVector) {

    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Icon(
            modifier = Modifier.size(dimensionResource(id = R.dimen.icon_size)),
            imageVector = trailingIcon,
            contentDescription = "go to previous screen",
            tint = Color.White
        )
        Text(
            text = title,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontSize = 22.sp,
            color = Color.White
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(modifier: Modifier) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {

        // Left icon
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = "Left Icon",
            modifier = Modifier
                .weight(0.1f)
                .size(dimensionResource(id = R.dimen.icon_size))
                .clickable { },
            tint = Color.White
        )

        OutlinedTextField(
            value = "",
            onValueChange = {},
            placeholder = { Text(text = "Search here", color = Color.White) },
            modifier = Modifier
                .run {
                    weight(0.8f)
                },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                focusedTextColor = Color.White,
                disabledTextColor = Color.White
            ),
        )

        // Right icon
        Icon(
            imageVector = Icons.Default.Clear,
            contentDescription = "Right Icon",
            modifier = Modifier
                .weight(0.1f)
                .size(dimensionResource(id = R.dimen.icon_size))
                .clickable { },
            tint = Color.White
        )
    }
}

@Composable
fun Content(viewModel: SearchViewModel, modifier: Modifier) {
    val items by viewModel.items.collectAsState()

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
                MovieCard(movie = item, onCardClicked = { viewModel.onMovieClicked()})
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MetapolitanTheme {
        SearchScreen(navController = rememberNavController())
    }
}