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
import com.example.paoabacus.ui.theme.*

data class MathStep(
    val stepNumber: Int,
    val instruction: String
)

data class MathLesson(
    val title: String,
    val problem: String,
    val rule: String,
    val steps: List<MathStep>
)

val LESSONS = listOf(
    MathLesson(
        title = "Addition 1-4",
        problem = "2 + 3",
        rule = "Move earth beads UP to the bar.",
        steps = listOf(
            MathStep(1, "Slide 2 Earth beads UP on the Ones rod. (Value: 2)"),
            MathStep(2, "Slide 3 more Earth beads UP on the Ones rod."),
            MathStep(3, "Look at the result: 5 beads are touching the bar. That is the value 5.")
        )
    ),
    MathLesson(
        title = "Adding 5",
        problem = "5 + 5",
        rule = "Move the Heaven bead DOWN, and clear the Earth beads.",
        steps = listOf(
            MathStep(1, "Slide 1 Heaven bead DOWN on the Ones rod. (Value: 5)"),
            MathStep(2, "To add 5, you cannot move another Heaven bead."),
            MathStep(3, "Move the Heaven bead back UP, and move 1 Earth bead UP on the TENS rod."),
            MathStep(4, "You carried the 10! The value is now 10.")
        )
    ),
    MathLesson(
        title = "Addition 6-9 (Borrowing)",
        problem = "8 + 6",
        rule = "This is the 'Small Friend' or 'Borrow' rule.",
        steps = listOf(
            MathStep(1, "Slide 1 Heaven bead DOWN and 3 Earth beads UP on the Ones rod. (Value: 8)"),
            MathStep(2, "To add 6, imagine the number 10. You need 2 more to reach 10."),
            MathStep(3, "Slide 2 Earth beads DOWN (away from the bar)."),
            MathStep(4, "Slide 1 Earth bead UP on the Tens rod. (You carried the 10)."),
            MathStep(5, "Look at the board: Tens rod has 1, Ones rod has 4. The answer is 14.")
        )
    ),
    MathLesson(
        title = "Subtraction 1-4",
        problem = "7 - 3",
        rule = "Move earth beads DOWN away from the bar.",
        steps = listOf(
            MathStep(1, "Make 7: 1 Heaven bead DOWN, 2 Earth beads UP. (Value: 7)"),
            MathStep(2, "Subtract 3: Move 3 Earth beads DOWN."),
            MathStep(3, "You have 1 Heaven bead (5) and 0 Earth beads. The answer is 5.")
        )
    )
)

@Composable
fun MathTeacherScreen() {
    var selectedLesson by remember { mutableStateOf(LESSONS[0]) }
    val scroll = rememberScrollState()

    Column(modifier = Modifier.fillMaxSize().background(DarkBg).verticalScroll(scroll).padding(16.dp)) {
        Text("📖 Math Teacher", color = Orange, fontSize = 26.sp, fontWeight = FontWeight.Bold)
        Text("Learn step-by-step how to move the beads.", color = LightGrey, fontSize = 14.sp)
        Spacer(Modifier.height(16.dp))

        // Lesson Selector
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
                ) {
                    Text(lesson.title.split(" ")[0], fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        // Current Lesson Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = DarkSurface),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(selectedLesson.title, color = Orange, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(8.dp))
                
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
                        Box(
                            modifier = Modifier.size(24.dp).background(Orange, RoundedCornerShape(12.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(step.stepNumber.toString(), color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        }
                        Spacer(Modifier.width(12.dp))
                        Text(step.instruction, color = White, fontSize = 15.sp, modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}
