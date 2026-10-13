package br.com.travelu.feature.login.di

import br.com.travelu.feature.login.data.datasource.AuthRemoteDataSource
import br.com.travelu.feature.login.data.repository.LoginRepositoryImpl
import br.com.travelu.feature.login.domain.repository.LoginRepository
import br.com.travelu.feature.login.domain.usecase.GetCurrentUserUseCase
import br.com.travelu.feature.login.domain.usecase.LoginUseCase
import br.com.travelu.feature.login.domain.usecase.LogoutUseCase
import br.com.travelu.feature.login.domain.usecase.RestoreSessionUseCase
import br.com.travelu.feature.login.presentation.LoginViewModel
import org.koin.dsl.module

val loginModule = module {
    single { AuthRemoteDataSource(get()) }
    single<LoginRepository> { LoginRepositoryImpl(get(), get()) }
    factory { LoginUseCase(get()) }
    factory { GetCurrentUserUseCase(get()) }
    factory { LogoutUseCase(get()) }
    factory { RestoreSessionUseCase(get()) }
    factory { LoginViewModel(get(), get(), get(), get()) }
}
