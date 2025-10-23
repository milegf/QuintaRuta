package com.example.quintaruta

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.quintaruta.data.local.AppDatabase
import com.example.quintaruta.data.repository.TriviaRepository
import com.example.quintaruta.data.seed.DataSeeder
import com.example.quintaruta.ui.navigation.AppNavigation
import com.example.quintaruta.ui.theme.QuintaRutaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = AppDatabase.getDatabase(applicationContext)
        val triviaRepository = TriviaRepository(database.triviaDao())

        DataSeeder.seedTrivias(triviaRepository)

        setContent {
            QuintaRutaTheme {
                AppNavigation()
            }
        }
    }
}
