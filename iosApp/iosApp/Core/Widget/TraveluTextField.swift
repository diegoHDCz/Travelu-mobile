import SwiftUI

// Campo de texto arredondado usado no login e no cadastro, com suporte a
// senha (alternar visibilidade) e mensagem de erro abaixo do campo.
struct TraveluTextField: View {
    let placeholder: LocalizedStringKey
    @Binding var text: String
    var error: String? = nil
    var isPassword: Bool = false
    var keyboardType: UIKeyboardType = .default
    var textContentType: UITextContentType? = nil

    @State private var isPasswordVisible = false

    var body: some View {
        VStack(alignment: .leading, spacing: 4) {
            HStack(spacing: 0) {
                field
                    .font(.subheadline)
                    .textInputAutocapitalization(isPassword || keyboardType == .emailAddress ? .never : .words)
                    .autocorrectionDisabled(isPassword || keyboardType == .emailAddress)
                    .keyboardType(keyboardType)
                    .textContentType(textContentType)
                    .frame(minHeight: 56)

                if isPassword {
                    Button {
                        isPasswordVisible.toggle()
                    } label: {
                        Image(systemName: isPasswordVisible ? "eye.slash.fill" : "eye.fill")
                            .foregroundStyle(Color.traveluSecondaryText)
                            .frame(width: 44, height: 44)
                    }
                    .buttonStyle(.plain)
                    .accessibilityLabel(isPasswordVisible ? "Hide password" : "Show password")
                }
            }
            .padding(.leading, 16)
            .padding(.trailing, isPassword ? 4 : 16)
            .background(Color.traveluFieldBackground, in: RoundedRectangle(cornerRadius: 16))
            .overlay {
                RoundedRectangle(cornerRadius: 16)
                    .stroke(Color.red, lineWidth: error == nil ? 0 : 1)
            }

            if let error {
                Text(error)
                    .font(.caption)
                    .foregroundStyle(.red)
                    .padding(.horizontal, 16)
            }
        }
        .padding(.horizontal, 16)
    }

    @ViewBuilder
    private var field: some View {
        if isPassword && !isPasswordVisible {
            SecureField(placeholder, text: $text)
        } else {
            TextField(placeholder, text: $text)
        }
    }
}

#Preview {
    VStack(spacing: 16) {
        TraveluTextField(placeholder: "Email Address", text: .constant(""), keyboardType: .emailAddress)
        TraveluTextField(placeholder: "Password", text: .constant("secret"), isPassword: true)
        TraveluTextField(placeholder: "Email Address", text: .constant("bad"), error: "Enter a valid email address")
    }
}
