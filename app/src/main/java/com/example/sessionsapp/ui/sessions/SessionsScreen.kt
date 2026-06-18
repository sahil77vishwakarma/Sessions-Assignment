package com.example.sessionsapp.ui.sessions

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun SessionsScreen(
    viewModel: SessionsViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val sessionsToShow = viewModel.filteredSessions

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Sessions",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Two toggle buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedButton(
                onClick = { viewModel.toggleWeightUnit() },
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = if (uiState.weightUnit.name == "KG") "kg" else "lb"
                )
            }

            OutlinedButton(
                onClick = { viewModel.toggleSpeedMode() },
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = if (uiState.speedMode.name == "FASTEST") "Fastest" else "Last"
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // filter chips row
        if (uiState.allTags.isNotEmpty()) {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                item {
                    FilterChip(
                        selected = uiState.selectedTag == null,
                        onClick = { viewModel.selectTag(null) },
                        label = { Text("All") },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = Color(0xFF9C89F6),
                            selectedLabelColor = Color.White,
                            containerColor = Color(0xFF2E2E2E),
                            labelColor = Color.White
                        )
                    )
                }

                // One chip per unique tag
                items(uiState.allTags) { tag ->
                    FilterChip(
                        selected = uiState.selectedTag == tag,
                        onClick = { viewModel.selectTag(tag) },
                        label = { Text(tag) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = Color(0xFF9C89F6),
                            selectedLabelColor = Color.White,
                            containerColor = Color(0xFF2E2E2E),
                            labelColor = Color.White
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
        }

        if (uiState.isLoading) {                         //loading state
            Text(text = "Loading...", color = Color.Gray)
        }
        else if (sessionsToShow.isEmpty()) {          // empty state
            Text(
                text = "No sessions found.",
                color = Color.Gray,
                fontSize = 16.sp
            )
        }
        else {                                            ///session list
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(
                    items = sessionsToShow,
                    key = { it.id }
                ) { session ->
                    SessionCard(
                        session = session,
                        weightUnit = uiState.weightUnit,
                        speedMode = uiState.speedMode
                    )
                }
            }
        }
    }
}