package com.pizzeriaapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pizzeriaapp.ui.PizzeriaScreen
import com.pizzeriaapp.ui.PizzeriaViewModel
import com.pizzeriaapp.ui.theme.PizzeriaAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PizzeriaAppTheme {
                val pizzeriaViewModel: PizzeriaViewModel = viewModel()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    PizzeriaScreen(
                        viewModel = pizzeriaViewModel,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}