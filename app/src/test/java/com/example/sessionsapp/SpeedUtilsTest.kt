package com.example.sessionsapp

import com.example.sessionsapp.data.Repetition
import com.example.sessionsapp.utils.SpeedMode
import com.example.sessionsapp.utils.formatSpeed
import kotlin.test.Test
import kotlin.test.assertEquals

class SpeedUtilsTest {

    // empty reps → "0.00"
    @Test
    fun `empty reps returns 0 00`() {
        assertEquals("0.00", formatSpeed(emptyList(), SpeedMode.FASTEST))
    }

    // example from instructions: [0.42, 0.39, 0.35]
    @Test
    fun `fastest returns 0 42`() {
        val reps = listOf(
            Repetition(0.42),
            Repetition(0.39),
            Repetition(0.35)
        )
        assertEquals("0.42", formatSpeed(reps, SpeedMode.FASTEST))
    }

    @Test
    fun `last returns 0 35`() {
        val reps = listOf(
            Repetition(0.42),
            Repetition(0.39),
            Repetition(0.35)
        )
        assertEquals("0.35", formatSpeed(reps, SpeedMode.LAST))
    }

    // 0.4 → "0.40" always 2 decimals
    @Test
    fun `speed always shows 2 decimal places`() {
        val reps = listOf(Repetition(0.4))
        assertEquals("0.40", formatSpeed(reps, SpeedMode.FASTEST))
    }
}