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
fun homeView() {
    val apiClient = remember { ApiClient() }
    var year by remember { mutableStateOf("2023") }
    var triggerFetch by remember { mutableStateOf(false) }
    var constructors by remember { mutableStateOf<List<Constructor>?>(null) }
    var isLoading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(triggerFetch) {
        if (triggerFetch) {
            isLoading = true
            try {
                constructors = apiClient.getConstructors(year).MRData.ConstructorTable.Constructors
            } catch (e: Exception) {
                error = e.message
            } finally {
                isLoading = false
                triggerFetch = false // Reset the trigger
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
            text = "Formula 1 Constructors for $year",
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Red)
                .padding(16.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

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
            )

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = { triggerFetch = true },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)
            ) {
                Text("Get Constructors", color = Color.White)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        when {
            isLoading -> CircularProgressIndicator()
            error != null -> Text("Error: $error", fontWeight = FontWeight.SemiBold)
            constructors.isNullOrEmpty() -> Text("No constructors found for $year", fontWeight = FontWeight.SemiBold)
            else -> ConstructorsList(constructors!!)
        }
    }
}

@Composable
fun ConstructorsList(constructors: List<Constructor>) {
    LazyColumn(
        contentPadding = PaddingValues(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(constructors) { constructor ->
            ConstructorCard(constructor)
        }
    }
}

@Composable
fun ConstructorCard(constructor: Constructor) {
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
                text = constructor.name,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Nationality: ${constructor.nationality}",
                textAlign = TextAlign.Center
            )
        }
    }
}
