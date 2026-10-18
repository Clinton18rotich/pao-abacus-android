package com.example.paoabacus.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.paoabacus.ui.theme.*

@Composable
fun AbacusScreen() {
    // State for 4 rods (Thousands, Hundreds, Tens, Ones)
    // Each rod has a heaven bead (index 0) and 4 earth beads (index 1-4)
    var rods by remember { mutableStateOf(List(4) { List(5) { false } }) }
    var displayValue by remember { mutableStateOf(0) }

    // Calculate value whenever beads change
    LaunchedEffect(rods) {
        var total = 0
        var multiplier = 1000 // Start with Thousands
        rods.forEach { rod ->
            var rodValue = 0
            if (rod[0]) rodValue += 5 // Heaven bead
            for (i in 1..4) { if (rod[i]) rodValue += 1 } // Earth beads
            total += rodValue * multiplier
            multiplier /= 10
        }
        displayValue = total
    }

    Column(modifier = Modifier.fillMaxSize().background(DarkBg).padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        // Digital Display
        Text("🧮 Abacus Trainer", color = Orange, fontSize = 26.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(16.dp))
        Text("Value: $displayValue", color = Green, fontSize = 48.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(24.dp))

        // The Abacus Frame
        Box(modifier = Modifier.fillMaxWidth().height(400.dp).background(Color(0xFF3E2723), shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp)).padding(8.dp)) {
            Row(modifier = Modifier.fillMaxSize(), horizontalArrangement = Arrangement.SpaceEvenly) {
                rods.forEachIndexed { rodIndex, rod ->
                    AbacusRod(
                        rodState = rod,
                        onToggle = { beadIndex ->
                            val newRods = rods.toMutableList()
                            val newRod = newRods[rodIndex].toMutableList()
                            newRod[beadIndex] = !newRod[beadIndex]
                            newRods[rodIndex] = newRod
                            rods = newRods
                        }
                    )
                }
            }
            // The Horizontal Bar (separates heaven and earth)
            Box(modifier = Modifier.align(Alignment.Center).fillMaxWidth().height(4.dp).background(Color(0xFF8D6E63)))
        }

        Spacer(Modifier.height(24.dp))
        Text("Tap beads to move them. Watch the value change!", color = LightGrey, fontSize = 14.sp)
        Spacer(Modifier.height(16.dp))
        Button(onClick = { rods = List(4) { List(5) { false } } }, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF555555))) {
            Text("Reset Abacus")
        }
    }
}

@Composable
fun AbacusRod(rodState: List<Boolean>, onToggle: (Int) -> Unit) {
    Column(
        modifier = Modifier.fillMaxHeight().width(60.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Heaven Bead (Top)
        Box(
            modifier = Modifier.size(50.dp).offset(y = if (rodState[0]) 0.dp else 20.dp)
                .shadow(4.dp, CircleShape).background(if (rodState[0]) Orange else Color(0xFFBDBDBD), CircleShape)
                .clickable { onToggle(0) },
            contentAlignment = Alignment.Center
        ) { Text("5", color = Color.Black, fontWeight = FontWeight.Bold) }

        // Horizontal Bar spacer
        Spacer(Modifier.height(8.dp))

        // Earth Beads (Bottom)
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            for (i in 1..4) {
                Box(
                    modifier = Modifier.size(50.dp).offset(y = if (rodState[i]) (-20).dp else 0.dp)
                        .shadow(2.dp, CircleShape).background(if (rodState[i]) Orange else Color(0xFFBDBDBD), CircleShape)
                        .clickable { onToggle(i) },
                    contentAlignment = Alignment.Center
                ) { Text("1", color = Color.Black, fontWeight = FontWeight.Bold) }
            }
        }
    }
}
