package br.com.travelu.feature.travel.domain.repository

import br.com.travelu.feature.travel.domain.model.Travel
import kotlinx.coroutines.flow.Flow

 interface TravelRepository {

    fun getAllTravelList(): Flow<List<Travel>>
    fun getTravelById(): Flow<Travel?>
}