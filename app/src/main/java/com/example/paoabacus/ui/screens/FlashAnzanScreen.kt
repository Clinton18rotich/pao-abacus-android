package com.example.paoabacus.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
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
import com.example.paoabacus.ui.theme.*
import kotlinx.coroutines.delay

@Composable
fun FlashAnzanScreen() {
    var gameState by remember { mutableStateOf("idle") } // idle, playing, answered
    var numbers by remember { mutableStateOf(listOf<Int>()) }
    var currentIndex by remember { mutableStateOf(0) }
    var userAnswer by remember { mutableStateOf("") }
    var resultMessage by remember { mutableStateOf("") }
    
    // We will visualize the running total on a mini 2-rod abacus (Tens and Ones)
    var tensValue by remember { mutableStateOf(0) }
    var onesValue by remember { mutableStateOf(0) }

    fun startGame() {
        numbers = List(5) { (1..9).random() }
        currentIndex = 0
        tensValue = 0
        onesValue = 0
        gameState = "playing"
        userAnswer = ""
        resultMessage = ""
    }

    // Game Loop
    LaunchedEffect(gameState, currentIndex) {
        if (gameState == "playing") {
            if (currentIndex < numbers.size) {
                // Add the current number to the running total
                val currentTotal = (tensValue * 10) + onesValue + numbers[currentIndex]
                tensValue = currentTotal / 10
                onesValue = currentTotal % 10
                
                delay(1200L) // Wait 1.2 seconds so you can see the beads move
                currentIndex++
            } else {
                gameState = "answered"
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize().background(DarkBg).padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Text("⚡ Flash Anzan", color = Orange, fontSize = 26.sp, fontWeight = FontWeight.Bold)
        Text("Watch the beads move. Don't count the numbers!", color = LightGrey, fontSize = 14.sp)
        Spacer(Modifier.height(24.dp))

        if (gameState == "idle") {
            Button(onClick = { startGame() }, colors = ButtonDefaults.buttonColors(containerColor = Orange, contentColor = Color.Black)) {
                Text("Start Game")
            }
        } else if (gameState == "playing") {
            Text("+ ${numbers[currentIndex]}", color = Orange, fontSize = 60.sp, fontWeight = FontWeight.Bold)
            
            Spacer(Modifier.height(24.dp))
            
            // Mini 2-Rod Abacus Visualization
            Box(
                modifier = Modifier.fillMaxWidth(0.8f).height(200.dp)
                    .shadow(8.dp, RoundedCornerShape(12.dp))
                    .background(Brush.verticalGradient(listOf(Color(0xFF6D4C41), Color(0xFF3E2723))), RoundedCornerShape(12.dp))
                    .padding(16.dp)
            ) {
                // Reckoning Bar
                Box(modifier = Modifier.align(Alignment.Center).fillMaxWidth().height(4.dp).background(Color(0xFFBCAAA4)))
                
                Row(modifier = Modifier.fillMaxSize(), horizontalArrangement = Arrangement.SpaceEvenly) {
                    // Tens Rod
                    MiniRod(value = tensValue)
                    // Ones Rod
                    MiniRod(value = onesValue)
                }
            }
            
        } else if (gameState == "answered") {
            Text("Enter the final sum:", color = LightGrey, fontSize = 16.sp)
            OutlinedTextField(
                value = userAnswer,
                onValueChange = { userAnswer = it },
                label = { Text("Sum") },
                modifier = Modifier.fillMaxWidth(0.5f),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Orange, focusedTextColor = White)
            )
            Spacer(Modifier.height(8.dp))
            Button(onClick = {
                val correctSum = numbers.sum()
                if (userAnswer.trim() == correctSum.toString()) {
                    resultMessage = "✅ Correct! The sum was $correctSum"
                } else {
                    resultMessage = "❌ Wrong. The sum was $correctSum"
                }
            }, colors = ButtonDefaults.buttonColors(containerColor = Green)) { Text("Submit") }

            if (resultMessage.isNotEmpty()) {
                Spacer(Modifier.height(16.dp))
                Text(resultMessage, color = if (resultMessage.startsWith("✅")) Green else Red, fontSize = 16.sp)
                Spacer(Modifier.height(16.dp))
                Button(onClick = { startGame() }, colors = ButtonDefaults.buttonColors(containerColor = Orange, contentColor = Color.Black)) { Text("Play Again") }
            }
        }
    }
}

@Composable
fun MiniRod(value: Int) {
    Column(modifier = Modifier.fillMaxHeight().width(60.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.SpaceBetween) {
        // Heaven Bead
        MiniBead(isActive = value >= 5)
        Spacer(Modifier.height(24.dp))
        // Earth Beads
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            for (i in 1..4) {
                MiniBead(isActive = (value % 5) >= i)
            }
        }
    }
}

@Composable
fun MiniBead(isActive: Boolean) {
    val offsetY by animateFloatAsState(
        targetValue = if (isActive) 0f else 4f,
        animationSpec = spring(dampingRatio = 0.6f, stiffness = 400f),
        label = "bead"
    )
    Box(
        modifier = Modifier
            .offset(y = offsetY.dp)
            .size(width = 50.dp, height = 20.dp)
            .shadow(if (isActive) 6.dp else 2.dp, CircleShape)
            .background(
                brush = Brush.verticalGradient(
                    colors = if (isActive) listOf(Color(0xFFFFD54F), Color(0xFFFF8F00)) else listOf(Color(0xFFA1887F), Color(0xFF4E342E))
                ),
                shape = CircleShape
            )
    )
}
