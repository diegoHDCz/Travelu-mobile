package br.com.travelu.feature.register.di

import br.com.travelu.feature.register.data.datasource.RegisterRemoteDataSource
import br.com.travelu.feature.register.data.repository.RegisterRepositoryImpl
import br.com.travelu.feature.register.domain.repository.RegisterRepository
import br.com.travelu.feature.register.domain.usecase.RegisterUseCase
import br.com.travelu.feature.register.presentation.RegisterViewModel
import org.koin.dsl.module

val registerModule = module {
    single { RegisterRemoteDataSource(get()) }
    single<RegisterRepository> { RegisterRepositoryImpl(get()) }
    factory { RegisterUseCase(get()) }
    factory { RegisterViewModel(get()) }
}
