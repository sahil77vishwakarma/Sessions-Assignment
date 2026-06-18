package com.example.sessionsapp.ui.sessions

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.sessionsapp.data.Session
import com.example.sessionsapp.data.SessionsResponse
import com.example.sessionsapp.utils.SpeedMode
import com.example.sessionsapp.utils.WeightUnit
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class SessionsUiState(
    val sessions: List<Session> = emptyList(),
    val weightUnit: WeightUnit = WeightUnit.KG,
    val speedMode: SpeedMode = SpeedMode.FASTEST,
    val isLoading: Boolean = true,
    val allTags: List<String> = emptyList(),
    val selectedTag: String? = null
)

class SessionsViewModel(application: Application) : AndroidViewModel(application) {

    private val _uiState = MutableStateFlow(SessionsUiState())
    val uiState: StateFlow<SessionsUiState> = _uiState.asStateFlow()

    init {
        loadSessions()
    }

    // Loads sessions.json from assets folder and parses it
    private fun loadSessions() {
        viewModelScope.launch {
            try {
                val jsonString = getApplication<Application>()                ///open
                    .assets
                    .open("sessions.json")
                    .bufferedReader()
                    .use { it.readText() }

                // parse JSON into our data classes using Gson
                val response = Gson().fromJson(jsonString, SessionsResponse::class.java)

                // Sort sessions by date — newest first
                val sorted = response.sessions.sortedByDescending { it.date }


                _uiState.update { current ->
                    current.copy(
                        sessions = sorted,
                        isLoading = false,
                        allTags = response.sessions
                            .flatMap { it.tags }
                            .distinct()
                            .sorted()
                    )
                }

            } catch (e: Exception) {
                _uiState.update { current ->
                    current.copy(isLoading = false)    ///emptylist
                }
            }
        }
    }


    fun toggleWeightUnit() {
        _uiState.update { current ->
            current.copy(
                weightUnit = if (current.weightUnit == WeightUnit.KG)
                    WeightUnit.LB else WeightUnit.KG
            )
        }
    }

    fun toggleSpeedMode() {
        _uiState.update { current ->
            current.copy(
                speedMode = if (current.speedMode == SpeedMode.FASTEST)
                    SpeedMode.LAST else SpeedMode.FASTEST
            )
        }
    }

    // Returns filtered sessions based on selected tag
    val filteredSessions: List<Session>
        get() {
            val state = _uiState.value
            return if (state.selectedTag == null) {
                state.sessions
            } else {
                state.sessions.filter { session ->
                    session.tags.contains(state.selectedTag)
                }
            }
        }

    fun selectTag(tag: String?) {
        _uiState.update { current ->
            current.copy(selectedTag = tag)
        }
    }
}