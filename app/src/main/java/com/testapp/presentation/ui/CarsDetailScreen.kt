package com.testapp.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.testapp.R
import com.testapp.domain.models.Car
import com.testapp.presentation.components.CarDetailTopAppBar
import com.testapp.utils.collectAsStateLifecycleAware


@Composable
fun CarsDetailScreen(
    car: Car,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = hiltViewModel(),
    scaffoldState: ScaffoldState = rememberScaffoldState(),
) {
    Scaffold(
        scaffoldState = scaffoldState,
        modifier = modifier.fillMaxSize(),
        topBar = {
            CarDetailTopAppBar(onBack = onBack, title = stringResource(R.string.car_details))
        },
    ) { paddingValues ->
        //observe for ui state changes
        val uiState by viewModel.uiState.collectAsStateLifecycleAware()
        //car Details content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(dimensionResource(id = R.dimen._16sdp))
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CarItemContent(car = car)
        }
        // Check for user messages to display on the screen
        uiState.msg?.let { userMessage ->
            LaunchedEffect(scaffoldState, viewModel, userMessage) {
                scaffoldState.snackbarHostState.showSnackbar(userMessage)
                //viewModel.snackMessageShown()
            }
        }
    }
}
