package br.com.travelu.core.network

import br.com.travelu.core.storage.SecureStore
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

class AuthSessionManagerTest {

    private class InMemorySecureStore : SecureStore {
        val values = mutableMapOf<String, String>()
        override fun get(key: String): String? = values[key]
        override fun put(key: String, value: String) { values[key] = value }
        override fun remove(key: String) { values.remove(key) }
    }

    @Test
    fun tokensSurviveANewInstance() {
        val store = InMemorySecureStore()
        AuthSessionManager(store).saveTokens(accessToken = "access", refreshToken = "refresh")

        val restored = AuthSessionManager(store)

        assertTrue(restored.isAuthenticated)
        assertEquals("access", restored.accessToken)
        assertEquals("refresh", restored.refreshToken)
    }

    @Test
    fun clearRemovesTokensFromStore() {
        val store = InMemorySecureStore()
        val session = AuthSessionManager(store)
        session.saveTokens(accessToken = "access", refreshToken = "refresh")

        session.clear()

        assertFalse(session.isAuthenticated)
        assertTrue(store.values.isEmpty())
        assertNull(AuthSessionManager(store).accessToken)
    }
}
