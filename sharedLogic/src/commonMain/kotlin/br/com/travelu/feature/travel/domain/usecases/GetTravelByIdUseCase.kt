package br.com.travelu.feature.travel.domain.usecases

import br.com.travelu.feature.travel.domain.model.Travel
import br.com.travelu.feature.travel.domain.repository.TravelRepository
import kotlinx.coroutines.flow.Flow

class GetTravelByIdUseCase(private val repository: TravelRepository) {
    fun execute(): Flow<Travel?> {
        return repository.getTravelById()
    }
}
