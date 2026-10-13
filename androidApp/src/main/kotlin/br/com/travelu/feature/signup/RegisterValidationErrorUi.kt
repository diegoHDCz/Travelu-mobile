package br.com.travelu.feature.signup

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import br.com.travelu.R
import br.com.travelu.feature.register.domain.validation.RegisterValidationError

@Composable
fun RegisterValidationError.message(): String = when (this) {
    RegisterValidationError.INVALID_EMAIL -> stringResource(R.string.error_invalid_email)
    RegisterValidationError.PASSWORDS_DO_NOT_MATCH -> stringResource(R.string.error_passwords_do_not_match)
}
