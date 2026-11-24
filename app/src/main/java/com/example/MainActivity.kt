package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.data.local.AppDatabase
import com.example.data.repository.ShayariRepository
import com.example.ui.navigation.YashShayariApp
import com.example.ui.theme.YashShayariTheme
import com.example.ui.viewmodel.ShayariViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val database = AppDatabase.getDatabase(applicationContext)
        val repository = ShayariRepository(database.shayariDao())

        setContent {
            YashShayariTheme {
                val viewModel: ShayariViewModel = viewModel(
                    factory = ShayariViewModel.Factory(repository)
                )
                YashShayariApp(viewModel = viewModel)
            }
        }
    }
}
