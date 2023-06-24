package com.testapp.presentation.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.testapp.domain.models.AppOperationState
import com.testapp.domain.models.Car
import com.testapp.domain.repository.CarRepository
import com.testapp.utils.SavableMutableSaveStateFlow
import com.testapp.utils.safeLaunchWithFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import javax.inject.Inject

const val COLOR_KEY = "COLOR_KEY"
const val PRICE_KEY = "PRICE_KEY"
const val CARS_LIST_KEY = "CARS_LIST_KEY"


@HiltViewModel
class MainViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val carRepository: CarRepository,
) : ViewModel() {

    //Mutable data
    private val _color = SavableMutableSaveStateFlow(savedStateHandle, COLOR_KEY, "")
    private val _price = SavableMutableSaveStateFlow(savedStateHandle, PRICE_KEY, "")
    private val _carList =
        SavableMutableSaveStateFlow<List<Car>>(savedStateHandle, CARS_LIST_KEY, emptyList())

    private val safeLaunchFlow = MutableStateFlow(AppOperationState())

    //Immutable data
    // The UI collects from this StateFlow to get its state updates
    @OptIn(ExperimentalCoroutinesApi::class)
    val uiState: StateFlow<HomeState> =
        safeLaunchFlow.flatMapLatest { operationState: AppOperationState ->
            //Handle Loading, Error and other screen states before accessing the combined screen data
            combineScreenData(operationState)
        }//.flowOn(Dispatchers.Main)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
                initialValue = HomeState()
            )

    //combine data flows to construct screen state
    private fun combineScreenData(operationState: AppOperationState) = combine(
        _color.asStateFlow(), _price.asStateFlow(), _carList.asStateFlow(),
    ) { color, price, carList ->
        when (operationState.status) {
            AppOperationState.DataStatus.LOADING -> HomeState(
                price = price,
                color = color,
                carList = emptyList(),
                isLoading = true
            )
            AppOperationState.DataStatus.ERROR -> {
                HomeState(
                    msg = operationState.error?.message,
                    isLoading = false,
                    price = price,
                    color = color,
                    carList = emptyList(),
                )
            }
            else -> {
                HomeState(
                    price = price,
                    color = color,
                    carList = carList,
                    isLoading = false,
                )
            }
        }
    }

    //Input Events- Actions
    fun onUpdateColor(newValue: String) {
        _color.value = newValue
    }

    fun onUpdatePrice(newValue: String) {
        _price.value = newValue
    }

    fun onSearch() {
        viewModelScope.safeLaunchWithFlow(safeLaunchFlow) {
            delay(2000)
            val resultList = carRepository.doSearchByColorAndPrice(_color.value, _price.value)
            _carList.value = resultList
        }
    }
}