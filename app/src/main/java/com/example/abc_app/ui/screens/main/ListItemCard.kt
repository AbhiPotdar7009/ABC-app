package com.example.abc_app.ui.screens.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import com.example.abc_app.R
import com.example.abc_app.domain.model.ListItem

@Composable
fun ListItemCard(item: ListItem) {
    Card(
        modifier = Modifier
            .padding(dimensionResource(id = R.dimen.padding_medium))
            .fillMaxWidth()
    ) {
        Row(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large))) {
            Image(
                painter = painterResource(id = item.imageRes),
                contentDescription = item.title,
                modifier = Modifier.size(dimensionResource(id = R.dimen.image_size_small))
            )
            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.width_medium)))
            Column {
                Text(text = item.title, style = MaterialTheme.typography.bodyLarge)
                Text(text = item.subtitle, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}