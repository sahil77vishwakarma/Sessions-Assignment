package com.example.sessionsapp.ui.sessions

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sessionsapp.data.Session
import com.example.sessionsapp.data.SessionSet
import com.example.sessionsapp.utils.SpeedMode
import com.example.sessionsapp.utils.WeightUnit
import com.example.sessionsapp.utils.convertWeight
import com.example.sessionsapp.utils.formatSpeed
import com.example.sessionsapp.utils.formatWeight
import com.example.sessionsapp.utils.weightUnitLabel
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

// Converts the ISO date from JSON into readable format
fun formatDate(isoDate: String): String {
    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH)
        inputFormat.timeZone = TimeZone.getTimeZone("UTC")
        val outputFormat = SimpleDateFormat("d MMM yyyy", Locale.ENGLISH)
        outputFormat.timeZone = TimeZone.getTimeZone("UTC")
        val date = inputFormat.parse(isoDate)
        outputFormat.format(date!!)
    } catch (e: Exception) {
        isoDate
    }
}


fun getBestSet(sets: List<SessionSet>, weightUnit: WeightUnit): String {

    val setsWithWeight = sets.filter { it.weight != null }
    if (setsWithWeight.isEmpty()) return ""

    val bestSet = setsWithWeight.maxByOrNull { set ->
        convertWeight(
            value = set.weight!!.value,
            fromUnit = set.weight.unit,
            targetUnit = weightUnit
        )
    } ?: return ""


    val weight = formatWeight(bestSet.weight, weightUnit)                              // Format the best set
    val unit = weightUnitLabel(weightUnit)
    val reps = bestSet.repCount ?: 0
    val rpe = bestSet.rpe ?: "-"

    return "🏆 Best Set: ${weight}${unit} × ${reps} rep || RPE ${rpe}"
}

@Composable
fun SessionCard(
    session: Session,
    weightUnit: WeightUnit,
    speedMode: SpeedMode
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1E1E1E)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = formatDate(session.date),
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Text(
                    text = "View",
                    color = Color(0xFF9C89F6),
                    fontSize = 14.sp
                )
            }


            if (session.tags.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    session.tags.forEach { tag ->
                        TagChip(tag)
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            SetsTable(
                sets = session.sets,
                weightUnit = weightUnit,
                speedMode = speedMode
            )

            val bestSetText = getBestSet(session.sets, weightUnit)
            if (bestSetText.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = bestSetText,
                    color = Color(0xFF9C89F6),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}


@Composable
fun TagChip(tag: String) {
    Box(
        modifier = Modifier
            .background(
                color = Color(0xFF2E2E2E),
                shape = RoundedCornerShape(20.dp)
            )
            .padding(horizontal = 10.dp, vertical = 4.dp)
    ) {
        Text(
            text = tag,
            color = Color.White,
            fontSize = 12.sp
        )
    }
}


@Composable
fun SetsTable(
    sets: List<SessionSet>,
    weightUnit: WeightUnit,
    speedMode: SpeedMode
) {
    val columns = listOf(
        "Weight(${weightUnitLabel(weightUnit)})",
        "Reps",
        "Type",
        "RPE",
        "Speed(m/s)"
    )

   //headers
    Row(modifier = Modifier.fillMaxWidth()) {
        columns.forEach { column ->
            Text(
                text = column,
                modifier = Modifier.weight(1f),
                color = Color.Gray,
                fontSize = 11.sp
            )
        }
    }

    Spacer(modifier = Modifier.height(6.dp))

    // Data rows
    sets.forEach { set ->
        Row(modifier = Modifier.fillMaxWidth()) {

            Text(
                text = formatWeight(set.weight, weightUnit),       // Weight column
                modifier = Modifier.weight(1f),
                color = Color.White,
                fontSize = 12.sp
            )

            Text(
                text = (set.repCount ?: 0).toString(),                               // Reps column
                modifier = Modifier.weight(1f),
                color = Color.White,
                fontSize = 12.sp
            )

            Text(
                text = set.setType ?: "--",                                      // Type column
                modifier = Modifier.weight(1f),
                color = Color.White,
                fontSize = 12.sp
            )

            Text(
                text = set.rpe ?: "-",                                               // RPE column
                modifier = Modifier.weight(1f),
                color = Color.White,
                fontSize = 12.sp
            )

            Text(
                text = formatSpeed(set.repetitions, speedMode),           // Speed column
                modifier = Modifier.weight(1f),
                color = Color.White,
                fontSize = 12.sp
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
    }
}