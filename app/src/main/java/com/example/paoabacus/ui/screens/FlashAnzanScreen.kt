package com.example.paoabacus.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.paoabacus.ui.theme.*
import kotlinx.coroutines.delay

@Composable
fun FlashAnzanScreen() {
    var numberSequence by remember { mutableStateOf(listOf<Int>()) }
    var currentIndex by remember { mutableStateOf(0) }
    var isPlaying by remember { mutableStateOf(false) }
    var userAnswer by remember { mutableStateOf("") }
    var resultMessage by remember { mutableStateOf("") }

    fun startGame() {
        numberSequence = List(5) { (1..9).random() }
        currentIndex = 0
        isPlaying = true
        userAnswer = ""
        resultMessage = ""
    }

    LaunchedEffect(isPlaying, currentIndex) {
        if (isPlaying && currentIndex < numberSequence.size) {
            delay(800L)
            currentIndex++
        } else if (isPlaying && currentIndex >= numberSequence.size) {
            isPlaying = false
        }
    }

    Column(modifier = Modifier.fillMaxSize().background(DarkBg).padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Text("⚡ Flash Anzan", color = Orange, fontSize = 26.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(8.dp))
        Text("Visualize the beads. Add the numbers!", color = LightGrey, fontSize = 14.sp)
        Spacer(Modifier.height(32.dp))

        if (isPlaying && currentIndex < numberSequence.size) {
            Text(numberSequence[currentIndex].toString(), color = Orange, fontSize = 80.sp, fontWeight = FontWeight.Bold)
        } else if (currentIndex >= numberSequence.size && numberSequence.isNotEmpty()) {
            Text("Enter the sum:", color = LightGrey, fontSize = 16.sp)
            OutlinedTextField(
                value = userAnswer,
                onValueChange = { userAnswer = it },
                label = { Text("Sum") },
                modifier = Modifier.fillMaxWidth(0.5f),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Orange, focusedTextColor = White)
            )
            Spacer(Modifier.height(8.dp))
            Button(onClick = {
                val correctSum = numberSequence.sum()
                if (userAnswer.trim() == correctSum.toString()) {
                    resultMessage = "✅ Correct! The sum was $correctSum"
                } else {
                    resultMessage = "❌ Wrong. The sum was $correctSum"
                }
            }, colors = ButtonDefaults.buttonColors(containerColor = Green)) { Text("Submit") }

            if (resultMessage.isNotEmpty()) {
                Spacer(Modifier.height(16.dp))
                Text(resultMessage, color = if (resultMessage.startsWith("✅")) Green else Red, fontSize = 16.sp)
            }
        }

        Spacer(Modifier.height(32.dp))
        Button(onClick = { startGame() }, colors = ButtonDefaults.buttonColors(containerColor = Orange, contentColor = Color.Black)) {
            Text(if (numberSequence.isEmpty()) "Start Game" else "Play Again")
        }
    }
}
