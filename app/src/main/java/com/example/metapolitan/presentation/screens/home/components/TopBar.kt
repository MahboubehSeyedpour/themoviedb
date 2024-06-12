package com.example.metapolitan.presentation.screens.home.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import com.example.metapolitan.R

@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    title: String,
    icon: ImageVector? = null,
    onSearchClick: () -> Unit = {}
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            color = Color.White,
            fontSize = 35.sp,
            modifier = Modifier
                .align(Alignment.CenterVertically)
        )
        if(icon == null) return
        IconButton(
            onClick = onSearchClick,
            modifier = Modifier.align(Alignment.CenterVertically)
        ) {
            Icon(
                modifier = Modifier.size(dimensionResource(id = R.dimen.icon_size)),
                imageVector = icon,
                contentDescription = "Search Icon",
                tint = Color.White
            )
        }
    }
}