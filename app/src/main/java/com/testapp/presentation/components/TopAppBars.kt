package com.testapp.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
fun CarDetailTopAppBar(onBack: () -> Unit, title: String) {
    TopAppBar(
        title = {
            Text(text = title)
        },
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(Icons.Filled.ArrowBack, contentDescription = "back")
            }
        },
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun MainScreenTopAppBar(title: String) {
    TopAppBar(
        title = {
            Text(text = title)
        },
        modifier = Modifier.fillMaxWidth()
    )
}
