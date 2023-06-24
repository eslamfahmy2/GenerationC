package com.testapp.presentation.ui

import androidx.compose.ui.test.assert
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.lifecycle.SavedStateHandle
import com.testapp.domain.models.Car
import com.testapp.domain.repository.CarRepository
import com.testapp.presentation.components.theme.AppMainTheme
import com.testapp.utils.BRAND_TAG
import com.testapp.utils.PLATE_NUMBER_TAG
import com.testapp.utils.PRICE_AND_COLOR_TAG
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CarsDetailScreenKtTest {

    //test rule for compose
    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var viewModel: MainViewModel
    private lateinit var carRepository: CarRepository
    private lateinit var car: Car

    @Before
    fun setUp() {
        carRepository = FakeCarRepository()
        car = FakeCarRepository().getCar()
        viewModel = MainViewModel(
            carRepository = carRepository,
            savedStateHandle = SavedStateHandle()
        )
        composeTestRule.setContent {
            AppMainTheme {
                CarsDetailScreen(
                    viewModel = viewModel,
                    car = car,
                    onBack = {}
                )
            }
        }
    }

    @Test
    fun testCarsDetailScreen() {

        composeTestRule.onNodeWithTag(BRAND_TAG).assertExists()
        composeTestRule.onNodeWithTag(PLATE_NUMBER_TAG).assertExists()
        composeTestRule.onNodeWithTag(PRICE_AND_COLOR_TAG).assertExists()
    }

    @Test
    fun testValidData() {
        composeTestRule.onNodeWithTag(BRAND_TAG).assert(hasText(car.brand))
        composeTestRule.onNodeWithTag(PLATE_NUMBER_TAG).assert(hasText(car.plate_number))
    }


}