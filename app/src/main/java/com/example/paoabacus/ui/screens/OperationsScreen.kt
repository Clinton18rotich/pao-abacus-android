package com.example.paoabacus.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.paoabacus.ui.theme.*

@Composable
fun OperationsScreen() {
    Column(modifier = Modifier.fillMaxSize().background(DarkBg).padding(16.dp)) {
        Text("✖️ Operations", color = Orange, fontSize = 26.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(16.dp))
        Text("Multiplication & Division coming soon!", color = LightGrey, fontSize = 16.sp)
        Spacer(Modifier.height(8.dp))
        Text("For now, practice addition on the Abacus tab and Flash Anzan tab.", color = Color(0xFFDDDDDD), fontSize = 14.sp)
    }
}
