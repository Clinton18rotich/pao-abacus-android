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
    MathLesson("Addition 1-4", "2 + 3", "Use the +5 Rule (Small Friend): Move Heaven DOWN and Earth DOWN.",
        listOf(
            MathStep(1, "Slide 2 Earth beads UP on the Ones rod. (Value: 2)"),
            MathStep(2, "You need to add 3. Since you only have 2 Earth beads left (out of 4), you must use the Heaven bead."),
            MathStep(3, "Slide 1 Heaven bead DOWN (value +5)."),
            MathStep(4, "Slide 2 Earth beads DOWN (value -2). Because 5 - 2 = 3."),
            MathStep(5, "Look at the board: 1 Heaven bead is DOWN, 0 Earth beads are UP. The result is 5.")
        ), 5),
    MathLesson("Adding 5", "5 + 5", "Use the +10 Rule: Move Heaven UP and carry 10 to the Tens rod.",
        listOf(
            MathStep(1, "Slide 1 Heaven bead DOWN on the Ones rod. (Value: 5)"),
            MathStep(2, "You need to add 5, but there is no more Heaven bead to move."),
            MathStep(3, "Slide the Heaven bead UP (value -5)."),
            MathStep(4, "Slide 1 Earth bead UP on the Tens rod (value +10)."),
            MathStep(5, "Result: Tens rod has 1 (10), Ones rod has 0. The answer is 10.")
        ), 10),
    MathLesson("Subtraction", "7 - 3", "Move Earth beads DOWN away from the bar.",
        listOf(
            MathStep(1, "Make 7: Slide 1 Heaven bead DOWN and 2 Earth beads UP. (Value: 7)"),
            MathStep(2, "To subtract 3, slide 3 Earth beads DOWN."),
            MathStep(3, "Look at the board: 1 Heaven bead is DOWN (5), 0 Earth beads are UP. The answer is 5.")
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
                Button(
                    onClick = {
                        PracticeState.targetValue = selectedLesson.targetNumber
                        onPracticeClick()
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
