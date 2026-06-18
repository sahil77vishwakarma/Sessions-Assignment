package com.example.sessionsapp.utils

import com.example.sessionsapp.data.WeightData

// Conversion factor
private const val KG_TO_LB = 2.20462

enum class WeightUnit {
    KG, LB
}

fun formatWeight(weight: WeightData?, targetUnit: WeightUnit): String {
    // null weight show "--"
    if (weight == null) return "--"

    //Convert to raw values
    val convertedValue = convertWeight(
        value = weight.value,
        fromUnit = weight.unit,
        targetUnit = targetUnit
    )

    // format teh number
    return formatWeightValue(convertedValue)
}

fun convertWeight(value: Double, fromUnit: String, targetUnit: WeightUnit): Double {
    return when {
        // kg to lb
        fromUnit == "kilogram" && targetUnit == WeightUnit.LB -> value * KG_TO_LB

        //lb to kg
        fromUnit == "pound" && targetUnit == WeightUnit.KG -> value / KG_TO_LB

        else -> value
    }
}


//display formatting
fun formatWeightValue(value: Double): String {
    return if (value % 1.0 == 0.0) {
        value.toInt().toString()    // 100.0 → "100"
    } else {
        "%.1f".format(value)        // 102.5 → "102.5"
    }
}


fun weightUnitLabel(unit: WeightUnit): String {
    return when (unit) {
        WeightUnit.KG -> "kg"
        WeightUnit.LB -> "lb"
    }
}