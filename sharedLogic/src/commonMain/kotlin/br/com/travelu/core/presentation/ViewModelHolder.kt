package br.com.travelu.core.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStore

// Holder para plataformas sem ViewModelStoreOwner pronto (iOS puro/SwiftUI sem
// Compose). Ele guarda o ViewModel num ViewModelStore próprio e expõe um
// clear() público para o Swift chamar no deinit, disparando o viewModelScope.cancel()
// interno do androidx. No Android/Compose isso não é necessário: use
// androidx.lifecycle.viewmodel.compose.viewModel { ... } ou koinViewModel<T>().
class ViewModelHolder<VM : ViewModel>(val viewModel: VM) {
    private val store = ViewModelStore().also { it.put(KEY, viewModel) }

    fun clear() = store.clear()

    private companion object {
        const val KEY = "holder"
    }
}
