package br.com.travelu.feature.travel.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.travelu.feature.travel.domain.usecases.GetAllTravelListUseCase
import br.com.travelu.feature.travel.domain.usecases.GetTravelByIdUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class TravelViewModel(
    private val getAllTravelListUseCase: GetAllTravelListUseCase,
    private val getTravelByIdUseCase: GetTravelByIdUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(TravelUiState())
    val uiState: StateFlow<TravelUiState> = _uiState.asStateFlow()

    init {
        loadTravels()
    }

    fun loadTravels() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
            getAllTravelListUseCase.execute()
                .catch { e ->
                    _uiState.value = _uiState.value.copy(isLoading = false, errorMessage = e.message)
                }
                .collect { travels ->
                    _uiState.value = _uiState.value.copy(isLoading = false, travels = travels)
                }
        }
    }

    fun onTravelSelected() {
        viewModelScope.launch {
            getTravelByIdUseCase.execute()
                .catch { e -> _uiState.value = _uiState.value.copy(errorMessage = e.message) }
                .collect { travel -> _uiState.value = _uiState.value.copy(selectedTravel = travel) }
        }
    }


    fun observeState(onChange: (TravelUiState) -> Unit) {
        viewModelScope.launch {
            uiState.collect { onChange(it) }
        }
    }
}
