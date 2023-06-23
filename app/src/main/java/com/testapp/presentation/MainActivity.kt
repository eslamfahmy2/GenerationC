package com.testapp.presentation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.testapp.presentation.components.navigation.MainAppGraph
import com.testapp.presentation.components.theme.MyApplicationTestUiAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTestUiAppTheme {
                MainAppGraph()
            }
        }
    }
}
