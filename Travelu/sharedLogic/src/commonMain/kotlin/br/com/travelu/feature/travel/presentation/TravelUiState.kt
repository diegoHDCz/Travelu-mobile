package br.com.travelu.feature.travel.presentation

import br.com.travelu.feature.travel.domain.model.Travel

data class TravelUiState(
    val isLoading: Boolean = false,
    val travels: List<Travel> = emptyList(),
    val selectedTravel: Travel? = null,
    val errorMessage: String? = null,
) {
    val hasListing: Boolean
        get() = travels.isNotEmpty()

    val showEmptyState: Boolean
        get() = !isLoading && !hasListing && errorMessage == null
}
