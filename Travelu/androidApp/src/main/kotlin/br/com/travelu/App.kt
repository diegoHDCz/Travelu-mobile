package br.com.travelu

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.travelu.feature.travel.data.datasource.DummyDataSource
import br.com.travelu.feature.travel.data.repository.TravelRepositoryImpl
import br.com.travelu.feature.travel.domain.usecases.GetAllTravelListUseCase
import br.com.travelu.feature.travel.domain.usecases.GetTravelByIdUseCase
import br.com.travelu.feature.travel.presentation.TravelViewModel
import br.com.travelu.feature.travel.ui.ListingScreen

@Composable
@Preview
fun App() {
    MaterialTheme {
        val viewModel = viewModel {
            val repository = TravelRepositoryImpl(DummyDataSource())
            TravelViewModel(
                GetAllTravelListUseCase(repository),
                getTravelByIdUseCase = GetTravelByIdUseCase(repository)
            )
        }
        ListingScreen(viewModel)
    }

}