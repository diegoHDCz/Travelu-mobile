package br.com.travelu

import br.com.travelu.core.di.initKoin
import br.com.travelu.core.presentation.ViewModelHolder
import br.com.travelu.feature.login.presentation.LoginViewModel
import br.com.travelu.feature.register.presentation.RegisterViewModel
import br.com.travelu.feature.travel.presentation.TravelViewModel
import org.koin.mp.KoinPlatform

// Ponte pro Swift: Kotlin/Native nao exporta argumentos com valor default
// nem funcoes genericas reificadas (get<T>()) pro ObjC/Swift, entao cada
// chamada que o iOS precisa fica encapsulada aqui, sem generics/defaults.
// Isso continua valendo mesmo com SKIE: SKIE resolve a exposicao de
// Flow/StateFlow pro Swift, mas nao o problema de reified generics do Koin.
fun doInitKoin() {
    initKoin()
}

// O holder é montado aqui e não registrado no Koin: o Koin resolve por classe
// (sem o generic), então ViewModelHolder<A> e ViewModelHolder<B> colidiriam.
fun getLoginViewModelHolder(): ViewModelHolder<LoginViewModel> =
    ViewModelHolder(KoinPlatform.getKoin().get<LoginViewModel>())

fun getRegisterViewModelHolder(): ViewModelHolder<RegisterViewModel> =
    ViewModelHolder(KoinPlatform.getKoin().get<RegisterViewModel>())

fun getTravelViewModelHolder(): ViewModelHolder<TravelViewModel> =
    ViewModelHolder(KoinPlatform.getKoin().get<TravelViewModel>())
