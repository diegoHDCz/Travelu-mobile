package br.com.travelu.feature.travel.data.repository

import br.com.travelu.feature.travel.data.datasource.DummyDataSource
import br.com.travelu.feature.travel.data.mapper.TravelMapper
import br.com.travelu.feature.travel.domain.model.Travel
import br.com.travelu.feature.travel.domain.repository.TravelRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map


class TravelRepositoryImpl(
    private val dataSource: DummyDataSource = DummyDataSource(),
) : TravelRepository {

    private val _listings = MutableStateFlow<List<Travel>>(emptyList())
    val listings: StateFlow<List<Travel>> = _listings.asStateFlow();

    override fun getAllTravelList(): Flow<List<Travel>> {
        return dataSource.listings.map {
            val domainModels = TravelMapper.toDomain(it)
            _listings.value = domainModels
            listings.value
        }
    }

    override fun getTravelById(): Flow<Travel?> =
        dataSource.listings.map { dtos -> dtos.firstOrNull()?.let { TravelMapper.toDomain(it) } }
}
