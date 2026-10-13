import SwiftUI
import SharedLogic

// Ponte Swift <-> RegisterViewModel compartilhado.
final class RegisterViewModelWrapper: ObservableObject {
    @Published private(set) var state: RegisterUiState

    private let holder: ViewModelHolder<RegisterViewModel>
    private var viewModel: RegisterViewModel { holder.viewModel }

    init() {
        let holder = KoinHelperKt.getRegisterViewModelHolder()
        self.holder = holder
        self.state = holder.viewModel.uiState.value
    }

    @MainActor
    func observe() async {
        for await newState in viewModel.uiState {
            state = newState
        }
    }

    func onNameChanged(_ name: String) {
        viewModel.onNameChanged(name: name)
        syncState()
    }

    func onEmailChanged(_ email: String) {
        viewModel.onEmailChanged(email: email)
        syncState()
    }

    func onPasswordChanged(_ password: String) {
        viewModel.onPasswordChanged(password: password)
        syncState()
    }

    func onConfirmPasswordChanged(_ confirmPassword: String) {
        viewModel.onConfirmPasswordChanged(confirmPassword: confirmPassword)
        syncState()
    }

    func onRegisterClicked() {
        viewModel.onRegisterClicked()
        // Erros de validação são aplicados de forma síncrona.
        syncState()
    }

    private func syncState() {
        state = viewModel.uiState.value
    }

    deinit {
        holder.clear()
    }
}

struct SignUpView: View {
    var onBackClick: () -> Void = {}
    var onSignInClick: () -> Void = {}
    var onRegisterSuccess: (_ email: String) -> Void = { _ in }

    @StateObject private var wrapper = RegisterViewModelWrapper()
    @FocusState private var focusedField: Field?

    private enum Field {
        case name, email, password, confirmPassword
    }

    var body: some View {
        ScrollView {
            VStack(spacing: 0) {
                HStack {
                    TraveluCircleImageButton(
                        systemName: "arrow.left",
                        accessibilityLabel: "Back",
                        action: onBackClick
                    )
                    Spacer()
                }

                Spacer().frame(height: 20)

                Text("Create account")
                    .font(.system(size: 32))
                    .multilineTextAlignment(.center)
                    .padding(16)

                Text("Please fill the details to continue")
                    .font(.system(size: 16))
                    .foregroundStyle(Color.traveluSecondaryText)
                    .multilineTextAlignment(.center)
                    .padding(.horizontal, 16)

                Spacer().frame(height: 26)

                TraveluTextField(
                    placeholder: "Full Name",
                    text: Binding(get: { wrapper.state.name }, set: wrapper.onNameChanged),
                    textContentType: .name
                )
                .focused($focusedField, equals: .name)
                .submitLabel(.next)
                .onSubmit { focusedField = .email }

                Spacer().frame(height: 16)

                TraveluTextField(
                    placeholder: "Email Address",
                    text: Binding(get: { wrapper.state.email }, set: wrapper.onEmailChanged),
                    error: wrapper.state.emailError?.message,
                    keyboardType: .emailAddress,
                    textContentType: .emailAddress
                )
                .focused($focusedField, equals: .email)
                .submitLabel(.next)
                .onSubmit { focusedField = .password }

                Spacer().frame(height: 16)

                TraveluTextField(
                    placeholder: "Password",
                    text: Binding(get: { wrapper.state.password }, set: wrapper.onPasswordChanged),
                    isPassword: true,
                    textContentType: .newPassword
                )
                .focused($focusedField, equals: .password)
                .submitLabel(.next)
                .onSubmit { focusedField = .confirmPassword }

                Spacer().frame(height: 16)

                TraveluTextField(
                    placeholder: "Confirm Password",
                    text: Binding(get: { wrapper.state.confirmPassword }, set: wrapper.onConfirmPasswordChanged),
                    error: wrapper.state.confirmPasswordError?.message,
                    isPassword: true,
                    textContentType: .newPassword
                )
                .focused($focusedField, equals: .confirmPassword)
                .submitLabel(.done)
                .onSubmit(register)

                if let errorMessage = wrapper.state.errorMessage {
                    Text(errorMessage)
                        .font(.system(size: 14))
                        .foregroundStyle(.red)
                        .frame(maxWidth: .infinity, alignment: .leading)
                        .padding(.horizontal, 16)
                        .padding(.vertical, 4)
                }

                Spacer().frame(height: 16)

                TraveluPrimaryButton(
                    title: "Sign Up",
                    isLoading: wrapper.state.isLoading,
                    isEnabled: wrapper.state.canSubmit,
                    action: register
                )

                HStack(spacing: 4) {
                    Text("Already have an account?")
                        .foregroundStyle(Color.traveluSecondaryText)
                    Button("Sign in", action: onSignInClick)
                }
                .padding(16)
            }
        }
        .scrollDismissesKeyboard(.interactively)
        .task {
            await wrapper.observe()
        }
        .onChange(of: wrapper.state.user?.id) { _, id in
            guard id != nil, let user = wrapper.state.user else { return }
            onRegisterSuccess(user.email ?? wrapper.state.email)
        }
    }

    private func register() {
        guard wrapper.state.canSubmit else { return }
        focusedField = nil
        wrapper.onRegisterClicked()
    }
}

#Preview {
    SignUpView()
}
