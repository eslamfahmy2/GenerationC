package com.testapp.presentation.ui

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.lifecycle.SavedStateHandle
import com.testapp.domain.repository.CarRepository
import com.testapp.presentation.components.theme.AppMainTheme
import com.testapp.utils.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

//Main search screen tests
@OptIn(ExperimentalCoroutinesApi::class)
class MainSearchScreenKtTest {

    //test rule for compose
    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var viewModel: MainViewModel
    private lateinit var carRepository: CarRepository

    @Before
    fun setUp() {
        carRepository = FakeCarRepository()
        viewModel = MainViewModel(
            carRepository = carRepository,
            savedStateHandle = SavedStateHandle()
        )
        composeTestRule.setContent {
            AppMainTheme {
                MainSearchScreen(
                    viewModel = viewModel
                ) {}
            }
        }
    }

    @Test
    fun testingMainSearchScreenContent() {
        // Start the app
        composeTestRule.onNodeWithTag(COLOR_TEXT_TAG).assertExists()
        composeTestRule.onNodeWithTag(PRICE_TEXT_TAG).assertExists()
        composeTestRule.onNodeWithTag(SEARCH_BUTTON_TAG).assertExists()
    }

    @Test
    fun testColorChange() {
        val color = "white"
        composeTestRule.onNodeWithTag(COLOR_TEXT_TAG).performTextInput(color)
        composeTestRule.onNodeWithTag(COLOR_TEXT_TAG).assert(hasText(color))
        Assert.assertEquals(viewModel.uiState.value.color, color)
    }

    @Test
    fun testButtonClearColor() {
        testColorChange()
        composeTestRule.onNodeWithTag(CLEAR_COLOR_BUTTON_TAG).performClick()
        composeTestRule.onNodeWithTag(COLOR_TEXT_TAG).assert(hasText(""))
        Assert.assertEquals(viewModel.uiState.value.color, "")
    }

    @Test
    fun testButtonClearPrice() {
        testPriceChange()
        composeTestRule.onNodeWithTag(CLEAR_PRICE_BUTTON_TAG).performClick()
        composeTestRule.onNodeWithTag(PRICE_TEXT_TAG).assert(hasText(""))
        Assert.assertEquals(viewModel.uiState.value.color, "")
    }

    @Test
    fun testPriceChange() {
        val price = "30"
        composeTestRule.onNodeWithTag(PRICE_TEXT_TAG).performTextInput(price)
        composeTestRule.onNodeWithTag(PRICE_TEXT_TAG).assert(hasText(price))
        Assert.assertEquals(viewModel.uiState.value.price, price)
    }

    @Test
    fun testSearchButton() = runTest {
        Assert.assertEquals(viewModel.uiState.value.isLoading, false)
        composeTestRule.onNodeWithTag(SEARCH_BUTTON_TAG).performClick()
        Assert.assertEquals(viewModel.uiState.value.isLoading, true)
    }
}