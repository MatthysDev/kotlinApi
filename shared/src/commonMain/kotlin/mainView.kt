import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.*

@Composable
fun mainView() {
    val apiClient = remember { ApiClient() }
    var drivers by remember { mutableStateOf<List<Driver>?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        try {
            drivers = apiClient.getDriversByYear().DriverTable.Drivers
        } catch (e: Exception) {
        } finally {
            isLoading = false
        }
    }

    when {
        isLoading -> Text("Loading...")
        error != null -> Text("Error: $error")
        drivers.isNullOrEmpty() -> Text("No drivers found")
        else -> DriversList(drivers!!)
    }
}

@Composable
fun DriversList(drivers: List<Driver>) {
    LazyColumn {
        items(drivers) { driver ->
            Text("${driver.givenName} ${driver.familyName}")
        }
    }
}
