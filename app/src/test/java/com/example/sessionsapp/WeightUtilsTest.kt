package com.example.sessionsapp


import com.example.sessionsapp.data.WeightData
import com.example.sessionsapp.utils.WeightUnit
import com.example.sessionsapp.utils.formatWeight
import org.junit.Test
import kotlin.test.assertEquals

class WeightUtilsTest {

    // null → "--"
    @Test
    fun `null weight returns double dash`() {
        assertEquals("--", formatWeight(null, WeightUnit.KG))
    }

    // button = lb examples from instructions
    @Test
    fun `100 kg with lb button shows 220 5`() {
        assertEquals("220.5", formatWeight(WeightData(100.0, "kilogram"), WeightUnit.LB))
    }

    @Test
    fun `102 5 kg with lb button shows 226 0`() {
        assertEquals("226.0", formatWeight(WeightData(102.5, "kilogram"), WeightUnit.LB))
    }

    @Test
    fun `220 lb with lb button shows 220`() {
        assertEquals("220", formatWeight(WeightData(220.0, "pound"), WeightUnit.LB))
    }

    // button = kg examples from instructions
    @Test
    fun `100 kg with kg button shows 100`() {
        assertEquals("100", formatWeight(WeightData(100.0, "kilogram"), WeightUnit.KG))
    }

    @Test
    fun `102 5 kg with kg button shows 102 5`() {
        assertEquals("102.5", formatWeight(WeightData(102.5, "kilogram"), WeightUnit.KG))
    }

    @Test
    fun `220 lb with kg button shows 99 8`() {
        assertEquals("99.8", formatWeight(WeightData(220.0, "pound"), WeightUnit.KG))
    }
}