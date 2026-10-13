package br.com.travelu.core.storage

import org.koin.core.module.Module

// Armazenamento chave/valor para segredos (tokens). Cada plataforma usa o
// cofre nativo: Keychain no iOS, Android Keystore + SharedPreferences no Android.
interface SecureStore {
    fun get(key: String): String?
    fun put(key: String, value: String)
    fun remove(key: String)
}

// Registra a implementação de SecureStore da plataforma no Koin.
expect val secureStoreModule: Module
