package com.testapp.presentation.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.withStyle
import androidx.hilt.navigation.compose.hiltViewModel
import com.testapp.R
import com.testapp.domain.models.Car
import com.testapp.presentation.components.MainScreenTopAppBar
import com.testapp.utils.collectAsStateLifecycleAware


//Main search screen state
data class HomeState(
    val color: String = "",
    val price: String = "",
    val isLoading: Boolean = false,
    val msg: String? = null,
    val carList: List<Car> = emptyList()
)

@Composable
fun MainSearchScreen(
    modifier: Modifier = Modifier,
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    viewModel: MainViewModel = hiltViewModel(),
    onCarClicked: (Car) -> Unit
) {
    Scaffold(
        scaffoldState = scaffoldState,
        modifier = modifier.fillMaxSize(),
        topBar = {
            MainScreenTopAppBar(title = stringResource(R.string.search))
        }
    ) { paddingValues ->

        val uiState by viewModel.uiState.collectAsStateLifecycleAware()

        Column(
            modifier = Modifier
                .wrapContentWidth()
                .padding(paddingValues)
                .padding(dimensionResource(id = R.dimen._16sdp))
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            //search view content
            SearchContent(
                color = uiState.color,
                price = uiState.price,
                onColorChanged = viewModel::onUpdateColor,
                onPriceChanged = viewModel::onUpdatePrice,
                onSearch = viewModel::onSearch
            )

            Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen._8sdp)))

            //loading content
            if (uiState.isLoading) {
                CircularProgressIndicator()
            } else //searched cars list
                uiState.carList.forEach { car ->
                    CarItemContent(car = car, modifier = Modifier.clickable {
                        onCarClicked(car)
                    })
                    Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen._6sdp)))
                }
        }

        // Check for user messages to display on the screen
        uiState.msg?.let { userMessage ->
            LaunchedEffect(scaffoldState, viewModel, userMessage, userMessage) {
                scaffoldState.snackbarHostState.showSnackbar(userMessage)
                viewModel.snackMessageShown()
            }
        }
    }
}


@Composable
fun SearchContent(
    color: String,
    onColorChanged: (String) -> Unit,
    price: String,
    onPriceChanged: (String) -> Unit,
    onSearch: () -> Unit
) {
    val focusManager = LocalFocusManager.current
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(dimensionResource(id = R.dimen._8sdp)),
            value = color,
            onValueChange = onColorChanged,
            placeholder = {
                Text(
                    modifier = Modifier.alpha(ContentAlpha.medium),
                    text = "color",
                    color = MaterialTheme.colors.onSurface
                )
            },
            textStyle = TextStyle(
                fontSize = MaterialTheme.typography.body1.fontSize
            ),
            singleLine = true,
            trailingIcon = {
                if (color.isNotEmpty()) {
                    IconButton(
                        onClick = {
                            onColorChanged("")
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close Icon",
                            tint = MaterialTheme.colors.primary
                        )
                    }
                }
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
        )

        Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen._8sdp)))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(dimensionResource(id = R.dimen._8sdp)),
            value = price,
            onValueChange = onPriceChanged,
            placeholder = {
                Text(
                    modifier = Modifier.alpha(ContentAlpha.medium),
                    text = "price",
                    color = MaterialTheme.colors.onSurface
                )
            },
            textStyle = TextStyle(
                fontSize = MaterialTheme.typography.body1.fontSize
            ),
            singleLine = true,
            trailingIcon = {
                if (price.isNotEmpty()) {
                    IconButton(
                        onClick = { onPriceChanged.invoke("") }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close Icon",
                            tint = MaterialTheme.colors.primary
                        )
                    }
                }
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
        )

        Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen._8sdp)))

        Button(
            onClick = {
                onSearch.invoke()
                focusManager.clearFocus()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Search")
        }
    }
}


@Composable
fun CarItemContent(car: Car, modifier: Modifier = Modifier) {

    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = dimensionResource(id = R.dimen._4sdp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen._12sdp)),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            val carBrandAndNumber = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = MaterialTheme.colors.primary,
                        fontWeight = FontWeight.Bold,
                        fontSize = MaterialTheme.typography.h1.fontSize,
                    )
                ) { append(car.brand) }

                withStyle(
                    style = SpanStyle(
                        color = MaterialTheme.colors.primary,
                        fontSize = MaterialTheme.typography.caption.fontSize,
                    )
                ) { append("\n\n${car.plate_number}") }
            }

            Text(
                modifier = Modifier
                    .wrapContentWidth(),
                text = carBrandAndNumber,
            )

            val carPriceAndColor = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = MaterialTheme.colors.primary,
                        fontWeight = FontWeight.Bold,
                        fontSize = MaterialTheme.typography.caption.fontSize,
                    )
                ) { append("${car.unit_price} ${car.currency}") }

                withStyle(
                    style = SpanStyle(
                        color = MaterialTheme.colors.primary,
                        fontSize = MaterialTheme.typography.caption.fontSize,
                    )
                ) { append("\n\n${car.color} color ") }
            }

            Text(
                modifier = Modifier
                    .wrapContentWidth(),
                text = carPriceAndColor,
            )
        }
    }
}
