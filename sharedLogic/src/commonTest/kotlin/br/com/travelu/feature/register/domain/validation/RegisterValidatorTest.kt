package br.com.travelu.feature.register.domain.validation

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class RegisterValidatorTest {

    @Test
    fun validEmailsPass() {
        listOf("user@travelu.com", "first.last+tag@mail.co.uk", "  user@travelu.com  ").forEach {
            assertNull(RegisterValidator.validateEmail(it), it)
        }
    }

    @Test
    fun invalidEmailsFail() {
        listOf("", "user", "user@", "@travelu.com", "user@travelu", "user travelu@mail.com").forEach {
            assertEquals(RegisterValidationError.INVALID_EMAIL, RegisterValidator.validateEmail(it), it)
        }
    }

    @Test
    fun matchingPasswordsPass() {
        assertNull(RegisterValidator.validatePasswordConfirmation("secret123", "secret123"))
    }

    @Test
    fun differentPasswordsFail() {
        assertEquals(
            RegisterValidationError.PASSWORDS_DO_NOT_MATCH,
            RegisterValidator.validatePasswordConfirmation("secret123", "secret12"),
        )
    }
}
