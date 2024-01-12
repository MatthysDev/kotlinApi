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

enum class SortOrder {
    NUMBER_ASC, NUMBER_DESC, NAME
}

@Composable
fun driverView() {
    val apiClient = remember { ApiClient() }
    var year by remember { mutableStateOf("2023") } // Default year set to 2023
    var yearForEffect by remember { mutableStateOf("2023") } // Default year set to 2023
    var drivers by remember { mutableStateOf<List<Driver>?>(null) }
    var isLoading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }
    var sortOrder by remember { mutableStateOf(SortOrder.NUMBER_ASC) }

    LaunchedEffect(yearForEffect) {
        if (yearForEffect.isNotEmpty()) {
            isLoading = true
            try {
                drivers = apiClient.getDriversByYear(yearForEffect).MRData.DriverTable.Drivers
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
            text = "Formula 1 Drivers",
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
                    .height(IntrinsicSize.Min) // Match the height to the Button
            )

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = {
                    error = null
                    drivers = null
                    yearForEffect = year // Trigger LaunchedEffect
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red),
                modifier = Modifier
                    .height(IntrinsicSize.Min) // Match the height to the TextField
                    .padding(start = 8.dp)
            ) {
                Text("Get Drivers", color = Color.White)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(onClick = { sortOrder = SortOrder.NUMBER_ASC }, colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)
            ) {
                Text("Number Asc", color = Color.White)
            }
            Button(onClick = { sortOrder = SortOrder.NUMBER_DESC }, colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)) {
                Text("Number Desc", color = Color.White)
            }
            Button(onClick = { sortOrder = SortOrder.NAME }, colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)) {
                Text("Alphabetic", color = Color.White)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        when {
            isLoading -> Text("Loading...", fontWeight = FontWeight.SemiBold)
            error != null -> Text("Error: $error", fontWeight = FontWeight.SemiBold)
            drivers.isNullOrEmpty() -> Text("No drivers found", fontWeight = FontWeight.SemiBold)
            else -> DriversList(drivers!!, sortOrder)
        }
    }
}

@Composable
fun DriverCard(driver: Driver) {
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
                text = "${driver.givenName} ${driver.familyName}",
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Number: ${driver.permanentNumber}",
                textAlign = TextAlign.Center
            )
            // Add more driver details here if needed
        }
    }
}

@Composable
fun DriversList(drivers: List<Driver>, sortOrder: SortOrder) {
    val sortedDrivers = when (sortOrder) {
        SortOrder.NUMBER_ASC -> drivers.sortedBy { it.permanentNumber }
        SortOrder.NUMBER_DESC -> drivers.sortedByDescending { it.permanentNumber }
        SortOrder.NAME -> drivers.sortedBy { "${it.givenName} ${it.familyName}" }
    }

    LazyColumn(
        contentPadding = PaddingValues(top = 8.dp, bottom = 64.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(sortedDrivers) { driver ->
            DriverCard(driver)
        }
    }
}
