package br.com.travelu.core.di

import br.com.travelu.feature.login.di.loginModule
import br.com.travelu.feature.travel.di.travelModule
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration

val sharedModules: List<Module> = listOf(loginModule, travelModule)

fun initKoin(
    extraModules: List<Module> = emptyList(),
    config: KoinAppDeclaration = {},
) = startKoin {
    config()
    modules(sharedModules + extraModules)
}