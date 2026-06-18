package com.example.sessionsapp.data

import com.google.gson.annotations.SerializedName

data class SessionsResponse(
    @SerializedName("exercise") val exercise: String,
    @SerializedName("sessions") val sessions: List<Session>
)

data class Session(
    @SerializedName("id")   val id: String,
    @SerializedName("date") val date: String,
    @SerializedName("tags") val tags: List<String>,
    @SerializedName("sets") val sets: List<SessionSet>
)

data class SessionSet(
    @SerializedName("id")           val id: String,
    @SerializedName("weight")       val weight: WeightData?,
    @SerializedName("repCount")     val repCount: Int?,
    @SerializedName("setType")      val setType: String?,
    @SerializedName("rpe")          val rpe: String?,
    @SerializedName("repetitions")  val repetitions: List<Repetition>
)

data class WeightData(
    @SerializedName("value") val value: Double,
    @SerializedName("unit")  val unit: String   // "kilogram" or "pound"
)

data class Repetition(
    @SerializedName("meanConcentricVelocity") val meanConcentricVelocity: Double
)