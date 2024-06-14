package com.example.metapolitan.presentation.screens.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.metapolitan.R
import com.example.metapolitan.presentation.components.MovieList
import com.example.metapolitan.presentation.theme.DarkGray
import com.example.metapolitan.presentation.theme.GunmetalGray
import com.example.metapolitan.presentation.theme.LightGray
import com.example.metapolitan.presentation.theme.MetapolitanTheme

@Composable
fun SearchScreen(navController: NavController, viewModel: SearchViewModel = hiltViewModel()) {


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
                .padding(dimensionResource(id = R.dimen.padding)),
            viewModel
        )

        MovieList(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize(),
            movies = viewModel.searchResultFlow.collectAsLazyPagingItems(),
            onMovieClicked = { movie -> viewModel.onMovieClicked(movie) }
        )
    }
}

@Composable
fun Header(modifier: Modifier, viewModel: SearchViewModel) {
    Column(modifier = modifier) {
        ScreenTitle(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.padding)),
            title = "Search",
            trailingIcon = Icons.AutoMirrored.Outlined.KeyboardArrowLeft
        )
        SearchBar(
            Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = dimensionResource(id = R.dimen.padding)),
            viewModel = viewModel
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

@Composable
fun SearchBar(modifier: Modifier, viewModel: SearchViewModel) {

    val keyboardController = LocalSoftwareKeyboardController.current
    val searchQuery by viewModel.searchQuery.collectAsState()

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
                .clickable { viewModel.onSearchButtonClicked() },
            tint = Color.White
        )

        OutlinedTextField(
            value = searchQuery,
            onValueChange = { value -> viewModel.onSearchQueryChanged(value) },
            placeholder = { Text(text = "Search here", color = LightGray) },
            modifier = Modifier
                .run {
                    weight(0.8f)
                },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                focusedTextColor = Color.White,
                disabledTextColor = Color.White
            ),
            maxLines = 1,
            keyboardOptions = KeyboardOptions().copy(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions {
                keyboardController?.hide()
                viewModel.onSearchButtonClicked()
            }
        )

        // Right icon
        Icon(
            imageVector = Icons.Default.Clear,
            contentDescription = "Right Icon",
            modifier = Modifier
                .weight(0.1f)
                .size(dimensionResource(id = R.dimen.icon_size))
                .clickable { viewModel.onQueryClear() },
            tint = Color.White
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MetapolitanTheme {
        SearchScreen(navController = rememberNavController())
    }
}