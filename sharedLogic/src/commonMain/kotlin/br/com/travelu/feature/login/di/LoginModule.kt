package br.com.travelu.feature.login.di

import br.com.travelu.feature.login.data.repository.LoginRepositoryImpl
import br.com.travelu.feature.login.domain.repository.LoginRepository
import br.com.travelu.feature.login.domain.usecase.LoginUseCase
import br.com.travelu.feature.login.presentation.LoginViewModel
import br.com.travelu.feature.login.presentation.LoginViewModelHolder
import org.koin.dsl.module

val loginModule = module {
    single<LoginRepository> { LoginRepositoryImpl() }
    factory { LoginUseCase(get()) }
    factory { LoginViewModel(get()) }
    factory { LoginViewModelHolder(get()) }
}
