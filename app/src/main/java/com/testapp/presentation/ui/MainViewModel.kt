package com.testapp.presentation.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.testapp.domain.models.AppOperationState
import com.testapp.domain.models.Car
import com.testapp.domain.repository.CarRepository
import com.testapp.utils.safeLaunchWithFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import javax.inject.Inject

const val COLOR_KEY = "COLOR_KEY"
const val PRICE_KEY = "PRICE_KEY"

@HiltViewModel
class MainViewModel @Inject constructor(
    private val carRepository: CarRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    //Mutable data
    private val _color = savedStateHandle.getStateFlow(COLOR_KEY, "")
    private val _price = savedStateHandle.getStateFlow(PRICE_KEY, "")
    private val _userMessage: MutableStateFlow<String?> = MutableStateFlow(null)
    private val _carList = MutableStateFlow<List<Car>>(emptyList())
    private val safeLaunchFlow = MutableStateFlow(AppOperationState())

    //Immutable data
    // The UI collects from this StateFlow to get its state updates
    val uiState: StateFlow<HomeState> =
        safeLaunchFlow.flatMapLatest { operationState: AppOperationState ->
            //Handle Loading, Error and other screen states before accessing the combined screen data
            combineScreenData(operationState)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
            initialValue = HomeState()
        )

    private fun combineScreenData(operationState: AppOperationState) = combine(
        _color, _price, _carList, _userMessage,
    ) { color, price, carList, userMessage ->
        when (operationState.status) {
            AppOperationState.DataStatus.LOADING -> HomeState(
                price = price,
                color = color,
                carList = emptyList(),
                msg = userMessage,
                isLoading = true
            )
            AppOperationState.DataStatus.ERROR -> HomeState(
                msg = operationState.error?.message,
                isLoading = false,
                price = price,
                color = color,
                carList = emptyList(),
            )
            else -> {
                HomeState(
                    price = price,
                    color = color,
                    carList = carList,
                    msg = userMessage
                )
            }
        }
    }

    //Input Events- Actions
    fun onUpdateColor(newValue: String) {
        savedStateHandle[COLOR_KEY] = newValue
    }

    fun onUpdatePrice(newValue: String) {
        savedStateHandle[PRICE_KEY] = newValue
    }

    fun snackMessageShown() {
        _userMessage.update { null }
    }

    fun onSearch() {
        viewModelScope.safeLaunchWithFlow(safeLaunchFlow) {
            delay(2000)
            val resultList = carRepository.doSearchByColorAndPrice(_color.value, _price.value)
            _carList.update { resultList }
        }
    }
}