package com.example.paoabacus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.paoabacus.ui.navigation.AbacusNavGraph
import com.example.paoabacus.ui.theme.PAOTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PAOTheme {
                AbacusNavGraph()
            }
        }
    }
}
