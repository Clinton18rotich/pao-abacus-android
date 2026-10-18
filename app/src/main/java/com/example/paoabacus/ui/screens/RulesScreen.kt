package com.example.paoabacus.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.paoabacus.data.ABACUS_RULES
import com.example.paoabacus.ui.theme.*

@Composable
fun RulesScreen() {
    Column(modifier = Modifier.fillMaxSize().background(DarkBg).padding(8.dp)) {
        Text("📊 Abacus Rules", color = Orange, fontSize = 22.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(8.dp))
        LazyColumn {
            items(ABACUS_RULES) { rule ->
                Column(modifier = Modifier.fillMaxWidth().background(DarkSurface).padding(12.dp)) {
                    Text("${rule.id}. ${rule.title}", color = Orange, fontWeight = FontWeight.Bold)
                    Text(rule.description, color = Color(0xFFDDDDDD), fontSize = 14.sp)
                }
                Spacer(Modifier.height(4.dp))
            }
        }
    }
}
