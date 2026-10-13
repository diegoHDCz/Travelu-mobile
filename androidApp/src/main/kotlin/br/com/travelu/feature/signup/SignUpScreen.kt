package br.com.travelu.feature.signup

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.travelu.core.widget.TraveluCircleImageButton
import br.com.travelu.core.widget.TraveluSpacer
import br.com.travelu.feature.register.presentation.RegisterUiState
import br.com.travelu.feature.register.presentation.RegisterViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SignUpScreen(
    viewModel: RegisterViewModel = koinViewModel(),
    onBackClick: () -> Unit = {},
    onSignInClick: () -> Unit = {},
    onRegisterSuccess: (email: String) -> Unit = {},
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState.user) {
        val user = uiState.user ?: return@LaunchedEffect
        onRegisterSuccess(user.email ?: uiState.email)
    }

    SignUpContent(
        uiState = uiState,
        onNameChanged = viewModel::onNameChanged,
        onEmailChanged = viewModel::onEmailChanged,
        onPasswordChanged = viewModel::onPasswordChanged,
        onConfirmPasswordChanged = viewModel::onConfirmPasswordChanged,
        onRegisterClick = viewModel::onRegisterClicked,
        onBackClick = onBackClick,
        onSignInClick = onSignInClick,
    )
}

@Composable
fun SignUpContent(
    uiState: RegisterUiState,
    onNameChanged: (String) -> Unit,
    onEmailChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onConfirmPasswordChanged: (String) -> Unit,
    onRegisterClick: () -> Unit,
    onBackClick: () -> Unit,
    onSignInClick: () -> Unit,
) {
    Scaffold {
        var passwordVisibility by remember { mutableStateOf(false) }
        var confirmPasswordVisibility by remember { mutableStateOf(false) }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .verticalScroll(rememberScrollState())
        ) {
            TraveluCircleImageButton(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back arrow",
                modifier = Modifier,
                onClick = onBackClick
            )
            TraveluSpacer(20.dp)
            Text(
                "Create account",
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                textAlign = TextAlign.Center,
                fontSize = 32.sp,
            )
            Text(
                "Please fill the details to continue",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )

            TraveluSpacer(26.dp)

            SignUpTextField(
                value = uiState.name,
                onValueChange = onNameChanged,
                placeholder = "Full Name",
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            )
            TraveluSpacer(16.dp)

            SignUpTextField(
                value = uiState.email,
                onValueChange = onEmailChanged,
                placeholder = "Email Address",
                error = uiState.emailError?.message(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next,
                ),
            )
            TraveluSpacer(16.dp)

            SignUpTextField(
                value = uiState.password,
                onValueChange = onPasswordChanged,
                placeholder = "Password",
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Next,
                ),
                isPassword = true,
                passwordVisible = passwordVisibility,
                onTogglePasswordVisibility = { passwordVisibility = !passwordVisibility },
            )
            TraveluSpacer(16.dp)

            SignUpTextField(
                value = uiState.confirmPassword,
                onValueChange = onConfirmPasswordChanged,
                placeholder = "Confirm Password",
                error = uiState.confirmPasswordError?.message(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done,
                ),
                isPassword = true,
                passwordVisible = confirmPasswordVisibility,
                onTogglePasswordVisibility = { confirmPasswordVisibility = !confirmPasswordVisibility },
            )

            if (uiState.errorMessage != null) {
                Text(
                    uiState.errorMessage.orEmpty(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 4.dp),
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 14.sp,
                )
            }

            TraveluSpacer(16.dp)

            Button(
                onClick = onRegisterClick,
                enabled = uiState.canSubmit,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .clip(RoundedCornerShape(6.dp)),
                shape = RoundedCornerShape(12.dp)
            ) {
                if (uiState.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        strokeWidth = 2.dp,
                        color = MaterialTheme.colorScheme.onPrimary,
                    )
                } else {
                    Text("Sign Up", modifier = Modifier.padding(vertical = 8.dp))
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Already have an account?",
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
                TextButton(onClick = onSignInClick) {
                    Text("Sign in", color = MaterialTheme.colorScheme.primary)
                }
            }
        }
    }
}

@Composable
private fun SignUpTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    keyboardOptions: KeyboardOptions,
    error: String? = null,
    isPassword: Boolean = false,
    passwordVisible: Boolean = false,
    onTogglePasswordVisibility: () -> Unit = {},
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        keyboardOptions = keyboardOptions,
        isError = error != null,
        supportingText = error?.let { { Text(it) } },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(
                MaterialTheme.colorScheme.surfaceVariant.copy(0.2f)
            ),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.surfaceVariant.copy(0.2f),
            unfocusedBorderColor = MaterialTheme.colorScheme.surfaceVariant.copy(0.2f)
        ),
        placeholder = {
            Text(
                placeholder,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onBackground.copy(0.7f)
            )
        },
        visualTransformation = if (isPassword && !passwordVisible) PasswordVisualTransformation() else VisualTransformation.None,
        trailingIcon = if (isPassword) {
            {
                Icon(
                    imageVector = if (!passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                    contentDescription = "$placeholder Icon",
                    modifier = Modifier
                        .size(48.dp)
                        .padding(12.dp)
                        .clickable(onClick = onTogglePasswordVisibility)
                )
            }
        } else null,
    )
}

@Composable
@Preview(showBackground = true)
fun SignUpScreenPreview() {
    SignUpContent(
        uiState = RegisterUiState(),
        onNameChanged = {},
        onEmailChanged = {},
        onPasswordChanged = {},
        onConfirmPasswordChanged = {},
        onRegisterClick = {},
        onBackClick = {},
        onSignInClick = {},
    )
}
