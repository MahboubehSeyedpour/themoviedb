package com.example.metapolitan.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.metapolitan.R

@Composable
fun RatingStars(rating: Float) {
    Row(modifier = Modifier.height(24.dp)) {
        repeat(5) { index ->
            val starRating = when {
                index < rating - 0.5 -> R.drawable.ic_star_full
                index < rating -> R.drawable.ic_star_half
                else -> R.drawable.ic_star_empty
            }
            Image(
                painter = painterResource(id = starRating),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}