package br.com.travelu

import br.com.travelu.core.di.initKoin
import br.com.travelu.core.presentation.ViewModelHolder
import br.com.travelu.feature.login.presentation.LoginViewModel
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

fun getLoginViewModelHolder(): ViewModelHolder<LoginViewModel> = KoinPlatform.getKoin().get()

fun getTravelViewModelHolder(): ViewModelHolder<TravelViewModel> = KoinPlatform.getKoin().get()
