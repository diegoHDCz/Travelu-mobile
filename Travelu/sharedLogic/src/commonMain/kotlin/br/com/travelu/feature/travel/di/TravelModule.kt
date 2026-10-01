package br.com.travelu.feature.travel.di

import br.com.travelu.feature.travel.data.datasource.DummyDataSource
import br.com.travelu.feature.travel.data.repository.TravelRepositoryImpl
import br.com.travelu.feature.travel.domain.repository.TravelRepository
import br.com.travelu.feature.travel.domain.usecases.GetAllTravelListUseCase
import br.com.travelu.feature.travel.domain.usecases.GetTravelByIdUseCase
import br.com.travelu.feature.travel.presentation.TravelViewModel
import org.koin.dsl.module

val travelModule = module {
    single { DummyDataSource() }
    single<TravelRepository> { TravelRepositoryImpl(get()) }
    factory { GetAllTravelListUseCase(get()) }
    factory { GetTravelByIdUseCase(get()) }
    factory { TravelViewModel(get(), get()) }
}
