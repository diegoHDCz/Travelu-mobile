package br.com.travelu.feature.travel.domain.usecases

import br.com.travelu.feature.travel.domain.model.Travel
import br.com.travelu.feature.travel.domain.repository.TravelRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetAllTravelListUseCase(private val repository: TravelRepository) {
    fun execute(): Flow<List<Travel>> {
        return repository.getAllTravelList().map { listings ->
            listings.sortedByDescending { it.rating }
        }
    }
}