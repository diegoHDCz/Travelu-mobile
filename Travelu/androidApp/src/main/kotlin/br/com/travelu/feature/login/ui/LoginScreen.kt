package br.com.travelu.feature.login.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.travelu.feature.login.data.repository.LoginRepositoryImpl
import br.com.travelu.feature.login.domain.usecase.LoginUseCase
import br.com.travelu.feature.login.presentation.LoginViewModel

// A View mora aqui (por plataforma). O estado/ViewModel mora em
// sharedLogic/feature/login/presentation, compartilhado com o iosApp.
@Composable
fun LoginScreen() {
    val viewModel = viewModel { LoginViewModel(LoginUseCase(LoginRepositoryImpl())) }

    val state by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        when {
            state.isLoading -> CircularProgressIndicator()
            state.userName != null -> Text("Bem-vindo, ${state.userName}!")
            else -> {
                Text(state.errorMessage ?: "Faça login")
                Button(onClick = { viewModel.onLoginClicked("teste@travelu.com", "123456") }) {
                    Text("Entrar")
                }
            }
        }
    }
}
