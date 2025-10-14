package com.example.quintaruta

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.quintaruta.ui.screens.AppNavigation
import com.example.quintaruta.ui.theme.QuintaRutaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QuintaRutaTheme {
                AppNavigation()
            }
        }
    }
}
