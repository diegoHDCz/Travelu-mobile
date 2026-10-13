package br.com.travelu.core.network

import br.com.travelu.core.storage.SecureStore

// Fonte única do par de tokens. Mantém uma cópia em memória para as leituras
// frequentes (toda requisição autenticada) e persiste no SecureStore para a
// sessão sobreviver ao fechamento do app.
class AuthSessionManager(private val store: SecureStore) {
    var accessToken: String? = store.get(ACCESS_TOKEN_KEY)
        private set
    var refreshToken: String? = store.get(REFRESH_TOKEN_KEY)
        private set

    val isAuthenticated: Boolean
        get() = accessToken != null

    fun saveTokens(accessToken: String, refreshToken: String) {
        this.accessToken = accessToken
        this.refreshToken = refreshToken
        store.put(ACCESS_TOKEN_KEY, accessToken)
        store.put(REFRESH_TOKEN_KEY, refreshToken)
    }

    fun clear() {
        accessToken = null
        refreshToken = null
        store.remove(ACCESS_TOKEN_KEY)
        store.remove(REFRESH_TOKEN_KEY)
    }

    private companion object {
        const val ACCESS_TOKEN_KEY = "access_token"
        const val REFRESH_TOKEN_KEY = "refresh_token"
    }
}
