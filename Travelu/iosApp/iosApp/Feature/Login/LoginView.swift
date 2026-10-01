import SwiftUI
import SharedLogic

// Ponte Swift <-> ViewModel compartilhado (mesmo LoginViewModel usado no Android).
final class LoginViewModelWrapper: ObservableObject {
    @Published var state: LoginUiState

    private let holder: LoginViewModelHolder
    private var viewModel: LoginViewModel { holder.viewModel }

    init() {
        let holder = KoinHelperKt.getLoginViewModelHolder()
        self.holder = holder
        // uiState.value chega como `Any?` porque o Kotlin/Native não preserva
        // o generic de StateFlow<T> (tipo de fora do nosso módulo) no export para ObjC.
        self.state = holder.viewModel.uiState.value as! LoginUiState

        holder.viewModel.observeState { [weak self] newState in
            DispatchQueue.main.async {
                self?.state = newState
            }
        }
    }

    func login() {
        viewModel.onLoginClicked(email: "teste@travelu.com", password: "123456")
    }

    deinit {
        holder.clear()
    }
}

struct LoginView: View {
    @StateObject private var wrapper = LoginViewModelWrapper()

    var body: some View {
        VStack(spacing: 16) {
            if wrapper.state.isLoading {
                ProgressView()
            } else if let userName = wrapper.state.userName {
                Text("Bem-vindo, \(userName)!")
            } else {
                Text(wrapper.state.errorMessage ?? "Faça login")
                Button("Entrar") {
                    wrapper.login()
                }
            }
        }
        .padding()
    }
}

struct LoginView_Previews: PreviewProvider {
    static var previews: some View {
        LoginView()
    }
}
