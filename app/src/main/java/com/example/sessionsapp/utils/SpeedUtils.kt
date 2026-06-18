package com.example.sessionsapp.utils

import com.example.sessionsapp.data.Repetition

enum class SpeedMode {
    FASTEST, LAST
}

// Return formated speed value
fun formatSpeed(repetitions: List<Repetition>, mode: SpeedMode): String {
    if (repetitions.isEmpty()) return "0.00"

    val speed = when (mode) {
        SpeedMode.FASTEST -> getFastestSpeed(repetitions)
        SpeedMode.LAST    -> getLastSpeed(repetitions)
    }

    // To show 2 decimal places
    return "%.2f".format(speed)
}

fun getFastestSpeed(repetitions: List<Repetition>): Double {
    return repetitions.maxOf { it.meanConcentricVelocity }
}

fun getLastSpeed(repetitions: List<Repetition>): Double {
    return repetitions.last().meanConcentricVelocity
}