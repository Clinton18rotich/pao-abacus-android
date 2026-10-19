package com.example.paoabacus.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.paoabacus.data.PracticeState
import com.example.paoabacus.ui.theme.*

@Composable
fun AbacusScreen() {
    var rods by remember { mutableStateOf(List(13) { List(5) { false } }) }
    var displayValue by remember { mutableStateOf(0L) }
    
    // Check if we need to highlight a practice target
    val target = PracticeState.targetValue

    LaunchedEffect(rods) {
        var total = 0L
        var multiplier = 1L
        for (i in 12 downTo 0) {
            var rodValue = 0
            if (rods[i][0]) rodValue += 5
            for (j in 1..4) { if (rods[i][j]) rodValue += 1 }
            total += rodValue * multiplier
            multiplier *= 10
        }
        displayValue = total
    }

    Column(modifier = Modifier.fillMaxSize().background(DarkBg).padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Text("🧮 Soroban", color = Orange, fontSize = 26.sp, fontWeight = FontWeight.Bold)
        
        if (target != null) {
            Text("🎯 Practice Target: Make $target", color = Green, fontSize = 16.sp, fontWeight = FontWeight.Bold)
        } else {
            Text("Value: $displayValue", color = Green, fontSize = 40.sp, fontWeight = FontWeight.Bold)
        }
        
        Spacer(Modifier.height(16.dp))

        Box(
            modifier = Modifier.fillMaxWidth().weight(1f).shadow(16.dp, RoundedCornerShape(16.dp))
                .background(Brush.verticalGradient(listOf(Color(0xFF6D4C41), Color(0xFF3E2723), Color(0xFF6D4C41))), RoundedCornerShape(16.dp))
                .padding(horizontal = 4.dp, vertical = 16.dp)
        ) {
            Box(modifier = Modifier.align(Alignment.Center).fillMaxWidth().height(6.dp).background(Color(0xFFBCAAA4), RoundedCornerShape(3.dp)).shadow(2.dp))
            
            Row(modifier = Modifier.fillMaxSize(), horizontalArrangement = Arrangement.SpaceEvenly, verticalAlignment = Alignment.CenterVertically) {
                rods.forEachIndexed { rodIndex, rod ->
                    SorobanRod(
                        rodState = rod,
                        targetNumber = if (rodIndex == 12) target else null, // Only highlight the Ones rod (index 12)
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
        }

        Spacer(Modifier.height(16.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Button(onClick = { rods = List(13) { List(5) { false } }; PracticeState.targetValue = null }, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF555555))) {
                Text("Reset")
            }
        }
    }
}

@Composable
fun SorobanRod(rodState: List<Boolean>, targetNumber: Int?, onToggle: (Int) -> Unit) {
    Column(modifier = Modifier.fillMaxHeight().width(24.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.SpaceBetween) {
        AnimatedBead(
            isActive = rodState[0], 
            isHighlighted = targetNumber != null && targetNumber >= 5,
            onClick = { onToggle(0) }
        )
        Spacer(Modifier.height(32.dp))
        Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
            for (i in 1..4) {
                AnimatedBead(
                    isActive = rodState[i], 
                    isHighlighted = targetNumber != null && (targetNumber % 5) >= i,
                    onClick = { onToggle(i) }
                )
            }
        }
    }
}

@Composable
fun AnimatedBead(isActive: Boolean, isHighlighted: Boolean, onClick: () -> Unit) {
    val offsetY by animateFloatAsState(targetValue = if (isActive) 0f else 4f, animationSpec = spring(dampingRatio = 0.5f, stiffness = 300f), label = "bead")

    // If highlighted, give it a glowing border
    val borderModifier = if (isHighlighted && !isActive) Modifier.background(Color.Transparent, CircleShape).padding(2.dp) else Modifier

    Box(
        modifier = borderModifier
            .padding(vertical = 2.dp).offset(y = offsetY.dp)
            .size(width = 22.dp, height = 16.dp)
            .shadow(if (isActive || isHighlighted) 6.dp else 2.dp, CircleShape)
            .background(
                brush = Brush.verticalGradient(
                    colors = when {
                        isActive -> listOf(Color(0xFFFFD54F), Color(0xFFFF8F00), Color(0xFFFFD54F)) // Gold
                        isHighlighted -> listOf(Color(0xFF81C784), Color(0xFF388E3C), Color(0xFF81C784)) // Glowing Green Hint
                        else -> listOf(Color(0xFFA1887F), Color(0xFF4E342E), Color(0xFFA1887F)) // Wood
                    }
                ),
                shape = CircleShape
            )
            .clickable { onClick() }
    )
}
