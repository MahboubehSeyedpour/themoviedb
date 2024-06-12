package com.example.metapolitan.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.metapolitan.R
import com.example.metapolitan.presentation.theme.BattleshipGray

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    modifier: Modifier,
    searchText: String,
    onFilterClicked: () -> Unit,
    searchTextChanged: (text: String) -> Unit
) {

    val keyboardController = LocalSoftwareKeyboardController.current

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            modifier = Modifier.weight(1f),
            value = searchText,
            onValueChange = {
                searchTextChanged(it)
            },
            placeholder = {
                Text(
                    text = "Search",
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = 13.sp
                    )
                )
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text).copy(
                imeAction = ImeAction.Search,
            ),
            keyboardActions = KeyboardActions(onSearch = {
                keyboardController?.hide()
            }),
            shape = RoundedCornerShape(40.dp),
            trailingIcon = {
                Icon(
                    modifier = Modifier
                        .width(16.dp)
                        .height(16.dp)
                        .size(dimensionResource(id = R.dimen.icon_size)),
                    imageVector = Icons.Outlined.Close,
                    contentDescription = "Search",
                    tint = Color.White
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = BattleshipGray,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            )
        )
//        Spacer(modifier = Modifier.width(6.dp))
//        IconButton(
//            onClick = { onFilterClicked() },
//            modifier = Modifier
//                .width(42.dp)
//                .aspectRatio(1f),
//        ) {
//            Icon(
//                imageVector = Icons.Outlined.Search,
//                contentDescription = "Mahboubeh"
//            )
//        }
    }
}