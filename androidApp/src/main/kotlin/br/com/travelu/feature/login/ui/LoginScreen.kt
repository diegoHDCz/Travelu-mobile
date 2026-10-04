package br.com.travelu.feature.login.ui


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.travelu.core.widget.TraveluCircleImageButton
import br.com.travelu.core.widget.TraveluSpacer
import br.com.travelu.feature.login.presentation.LoginViewModel
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun LoginScreen(
    viewModel: LoginViewModel = koinViewModel(),
    onLoginSuccess: () -> Unit = {},
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    LaunchedEffect(uiState.userName) {
        if (uiState.userName != null) onLoginSuccess()
    }

    val userName = uiState.userName
    if (userName != null) {
        LoggedInContent(userName = userName, onLogoutClicked = viewModel::onLogoutClicked)
        return
    }

    Scaffold {

        var passwordVisibility by remember { mutableStateOf<Boolean>(false) }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            TraveluCircleImageButton(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back arrow",
                modifier = Modifier,
                onClick = {}
            )
            TraveluSpacer(20.dp)
            Text(
                "Sign in now",
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                textAlign = TextAlign.Center,
                fontSize = 32.sp,

                )
            Text(
                "Please sign in to continue our app",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )

            TraveluSpacer(26.dp)

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
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
                        "Email Address",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onBackground.copy(0.7f)
                    )
                }
            )
            TraveluSpacer(16.dp)

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
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
                        "Password",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onBackground.copy(0.7f)
                    )
                },
                visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    Image(
                        imageVector = if (!passwordVisibility) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = "Password Icon",
                        modifier = Modifier
                            .size(48.dp)
                            .padding(12.dp)
                            .clickable {
                                passwordVisibility = !passwordVisibility
                            }
                    )
                }
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

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.weight(1f))
                TextButton(onClick = {}) {
                    Text(
                        "Forgot Password?",
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
            Button(
                onClick = { viewModel.onLoginClicked(email, password) },
                enabled = !uiState.isLoading && email.isNotBlank() && password.isNotBlank(),
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
                    Text("Sign In", modifier = Modifier.padding(vertical = 8.dp))
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
                    "Don't have an acount?",
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
                TextButton(onClick = {}) {
                    Text("Sign up", color = MaterialTheme.colorScheme.primary)
                }
            }
        }
    }
}

@Composable
private fun LoggedInContent(userName: String, onLogoutClicked: () -> Unit) {
    Scaffold {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                "Welcome, $userName!",
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
            )
            TraveluSpacer(16.dp)
            Button(onClick = onLogoutClicked) {
                Text("Log out", modifier = Modifier.padding(vertical = 8.dp))
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun LoginScreenPreview() {
    LoginScreen()
}
