package br.com.travelu.feature.travel.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.travelu.feature.travel.domain.model.Travel
import br.com.travelu.feature.travel.presentation.TravelViewModel

@Composable
fun ListingScreen(viewModel: TravelViewModel) {
    Scaffold {
        val listingState = viewModel.uiState.collectAsStateWithLifecycle()

        LazyColumn(modifier = Modifier.padding(it)) {
            items(listingState.value.travels) { listing ->
                TravelListingItem(listing = listing)

            }
        }
    }
}

@Composable
fun TravelListingItem(listing: Travel) {
    Column(
        modifier = Modifier
            .padding(16.dp)
    ) {
        Text(text = listing.title)
        Text(text = listing.description)
    }
}