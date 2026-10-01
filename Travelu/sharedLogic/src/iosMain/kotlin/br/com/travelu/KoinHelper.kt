package br.com.travelu

import br.com.travelu.core.di.initKoin
import br.com.travelu.feature.login.presentation.LoginViewModelHolder
import org.koin.mp.KoinPlatform

// Ponte pro Swift: Kotlin/Native nao exporta argumentos com valor default
// nem funcoes genericas reificadas (get<T>()) pro ObjC/Swift, entao cada
// chamada que o iOS precisa fica encapsulada aqui, sem generics/defaults.
fun doInitKoin() {
    initKoin()
}

fun getLoginViewModelHolder(): LoginViewModelHolder = KoinPlatform.getKoin().get()
