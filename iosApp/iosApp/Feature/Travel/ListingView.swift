import SwiftUI
import SharedLogic

// Ponte Swift <-> TravelViewModel compartilhado.
final class TravelViewModelWrapper: ObservableObject {
    @Published private(set) var state: TravelUiState

    private let holder: ViewModelHolder<TravelViewModel>
    private var viewModel: TravelViewModel { holder.viewModel }

    init() {
        let holder = KoinHelperKt.getTravelViewModelHolder()
        self.holder = holder
        self.state = holder.viewModel.uiState.value
    }

    @MainActor
    func observe() async {
        for await newState in viewModel.uiState {
            state = newState
        }
    }

    func loadTravels() {
        viewModel.loadTravels()
    }

    deinit {
        holder.clear()
    }
}

struct ListingView: View {
    @StateObject private var wrapper = TravelViewModelWrapper()

    var body: some View {
        List(wrapper.state.travels, id: \.self) { listing in
            TravelListingItem(listing: listing)
        }
        .listStyle(.plain)
        .overlay {
            if wrapper.state.isLoading && !wrapper.state.hasListing {
                ProgressView()
            } else if let errorMessage = wrapper.state.errorMessage {
                ContentUnavailableView {
                    Label(errorMessage, systemImage: "exclamationmark.triangle")
                } actions: {
                    Button("Try again", action: wrapper.loadTravels)
                }
            }
        }
        .task {
            await wrapper.observe()
        }
    }
}

struct TravelListingItem: View {
    let listing: Travel

    var body: some View {
        VStack(alignment: .leading, spacing: 4) {
            Text(listing.title)
            Text(listing.description_)
                .foregroundStyle(.secondary)
        }
        .padding(.vertical, 8)
    }
}

#Preview {
    ListingView()
}
