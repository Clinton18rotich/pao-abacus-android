package com.example.paoabacus.data

data class AbacusRule(
    val id: Int,
    val title: String,
    val description: String
)

val ABACUS_RULES = listOf(
    AbacusRule(1, "The Frame", "A soroban has rods for each place value: ones, tens, hundreds, thousands."),
    AbacusRule(2, "Heaven Bead", "The single bead above the bar is worth 5."),
    AbacusRule(3, "Earth Beads", "The four beads below the bar are each worth 1."),
    AbacusRule(4, "Adding 1-4", "Move earth beads up to the bar with your thumb."),
    AbacusRule(5, "Adding 5", "Move the heaven bead down to the bar with your index finger."),
    AbacusRule(6, "Adding 6-9", "Move the heaven bead and some earth beads together (e.g., 8 = 5 + 3)."),
    AbacusRule(7, "The +5 Rule", "To add 5 to a number 5-9, move a heaven bead and subtract earth beads (e.g., 5 + 7: move heaven down, move 2 earth beads down)."),
    AbacusRule(8, "The +10 Rule (Borrow)", "To add a number that exceeds 9 on one rod, move 1 bead up on the next rod to the left (carry the 10).")
)
