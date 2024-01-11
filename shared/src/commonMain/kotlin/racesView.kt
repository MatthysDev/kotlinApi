package com.example.yourapp

import ApiClient
import Race
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun racesView() {
    val apiClient = remember { ApiClient() }
    var year by remember { mutableStateOf("2023") }
    var yearForEffect by remember { mutableStateOf("2023") }
    var races by remember { mutableStateOf<List<Race>?>(null) }
    var isLoading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(yearForEffect) {
        if (yearForEffect.isNotEmpty()) {
            isLoading = true
            try {
                races = apiClient.getRacesByYear(yearForEffect).MRData.RaceTable.Races
            } catch (e: Exception) {
                error = e.message
            } finally {
                isLoading = false
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Formula 1 Races",
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Red)
                .padding(16.dp)
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = year,
                onValueChange = { year = it },
                label = { Text("Enter Year") },
                singleLine = true,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.Black
                ),
                modifier = Modifier
                    .weight(1f)
                    .height(IntrinsicSize.Min)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = {
                    error = null
                    races = null
                    yearForEffect = year
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red),
                modifier = Modifier
                    .height(IntrinsicSize.Min)
                    .padding(start = 8.dp)
            ) {
                Text("Get Races", color = Color.White)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        when {
            isLoading -> Text("Loading...", fontWeight = FontWeight.SemiBold)
            error != null -> Text("Error: $error", fontWeight = FontWeight.SemiBold)
            races.isNullOrEmpty() -> Text("No races found", fontWeight = FontWeight.SemiBold)
            else -> RacesList(races!!)
        }
    }
}

@Composable
fun RacesList(races: List<Race>) {
    LazyColumn(
        contentPadding = PaddingValues(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(races) { race ->
            RaceCard(race)
        }
    }
}

@Composable
fun RaceCard(race: Race) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = race.raceName,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Location: ${race.Circuit.circuitName}",
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Date: ${race.date}",
                textAlign = TextAlign.Center
            )
        }
    }
}

