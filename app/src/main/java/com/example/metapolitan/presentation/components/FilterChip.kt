package com.example.metapolitan.presentation.components

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.SelectableChipColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.metapolitan.R
import com.example.metapolitan.presentation.theme.BattleshipGray

@Composable
fun FilterChips(
    chipLabel: String,
    selected: Boolean,
    onChipClicked: (String) -> Unit,
    colors: SelectableChipColors = FilterChipDefaults.filterChipColors(
        containerColor = BattleshipGray,
        labelColor = Color.White,
        disabledLabelColor = Color.White,
        selectedLabelColor = Color.White
    ),
    shape: Shape = RoundedCornerShape(45.dp)
) {
    FilterChip(
        modifier = Modifier
            .fillMaxHeight()
            .padding(dimensionResource(id = R.dimen.padding).div(2)),
        label = {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(6.dp),
                text = chipLabel,
                style = TextStyle(
                    fontSize = 15.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.SemiBold
                )
            )
        },
        colors = colors,
        shape = shape,
        selected = selected,
        onClick = { onChipClicked(chipLabel) },
        border = null
    )
}