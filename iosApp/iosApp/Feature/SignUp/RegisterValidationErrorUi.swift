import Foundation
import SharedLogic

// Equivalente ao RegisterValidationErrorUi.kt: o enum vem do sharedLogic e o
// texto localizado fica na plataforma (Localizable.xcstrings).
extension RegisterValidationError {
    var message: String {
        switch self {
        case .invalidEmail:
            String(localized: "Enter a valid email address")
        case .passwordsDoNotMatch:
            String(localized: "Passwords do not match")
        }
    }
}
