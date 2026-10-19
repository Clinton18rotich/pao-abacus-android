package com.example.paoabacus.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.paoabacus.data.PracticeState
import com.example.paoabacus.ui.theme.*

data class MathStep(val stepNumber: Int, val instruction: String)
data class MathLesson(
    val title: String, val problem: String, val rule: String, 
    val steps: List<MathStep>, val targetNumber: Int
)

val LESSONS = listOf(
    MathLesson("Addition 1-4", "2 + 3", "Move earth beads UP to the bar.", listOf(
        MathStep(1, "Slide 2 Earth beads UP on the Ones rod."),
        MathStep(2, "Slide 3 more Earth beads UP."),
        MathStep(3, "The result is 5.")
    ), 5),
    MathLesson("Adding 5", "5 + 5", "Move Heaven bead DOWN, clear Earth beads.", listOf(
        MathStep(1, "Slide 1 Heaven bead DOWN. (Value: 5)"),
        MathStep(2, "To add 5, you carry the 10."),
        MathStep(3, "Move 1 Earth bead UP on the TENS rod.")
    ), 10),
    MathLesson("Borrowing", "8 + 6", "Small Friend / Borrow rule.", listOf(
        MathStep(1, "Make 8: 1 Heaven DOWN, 3 Earth UP."),
        MathStep(2, "To add 6, slide 2 Earth DOWN and 1 Earth UP on the Tens rod.")
    ), 14),
    MathLesson("Subtraction", "7 - 3", "Move earth beads DOWN.", listOf(
        MathStep(1, "Make 7: 1 Heaven DOWN, 2 Earth UP."),
        MathStep(2, "Move 3 Earth beads DOWN.")
    ), 4)
)

@Composable
fun MathTeacherScreen(onPracticeClick: () -> Unit) {
    var selectedLesson by remember { mutableStateOf(LESSONS[0]) }
    val scroll = rememberScrollState()

    Column(modifier = Modifier.fillMaxSize().background(DarkBg).verticalScroll(scroll).padding(16.dp)) {
        Text("📖 Math Teacher", color = Orange, fontSize = 26.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            LESSONS.forEach { lesson ->
                Button(
                    onClick = { selectedLesson = lesson },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedLesson == lesson) Orange else DarkSurface,
                        contentColor = if (selectedLesson == lesson) Color.Black else LightGrey
                    ),
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(4.dp)
                ) { Text(lesson.title.split(" ")[0], fontSize = 12.sp, fontWeight = FontWeight.Bold) }
            }
        }

        Spacer(Modifier.height(16.dp))

        Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = DarkSurface), shape = RoundedCornerShape(12.dp)) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(selectedLesson.title, color = Orange, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Text("Problem: ${selectedLesson.problem}", color = White, fontSize = 28.sp, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(8.dp))
                Box(modifier = Modifier.background(Color(0xFF1E2A1E), RoundedCornerShape(8.dp)).padding(10.dp)) {
                    Text("💡 Rule: ${selectedLesson.rule}", color = Green, fontSize = 14.sp, fontWeight = FontWeight.Medium)
                }
                Spacer(Modifier.height(16.dp))
                Text("Step-by-Step Guide:", color = LightGrey, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(8.dp))
                selectedLesson.steps.forEach { step ->
                    Row(modifier = Modifier.padding(bottom = 12.dp)) {
                        Box(modifier = Modifier.size(24.dp).background(Orange, RoundedCornerShape(12.dp)), contentAlignment = Alignment.Center) {
                            Text(step.stepNumber.toString(), color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        }
                        Spacer(Modifier.width(12.dp))
                        Text(step.instruction, color = White, fontSize = 15.sp, modifier = Modifier.weight(1f))
                    }
                }
                Spacer(Modifier.height(16.dp))
                
                // The Practice Button!
                Button(
                    onClick = { 
                        PracticeState.targetValue = selectedLesson.targetNumber
                        onPracticeClick() // Navigate to Abacus tab
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Green, contentColor = White)
                ) {
                    Text("🧮 Practice on the Abacus!", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
