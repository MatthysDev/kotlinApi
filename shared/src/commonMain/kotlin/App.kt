import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.yourapp.racesView
import org.jetbrains.compose.resources.ExperimentalResourceApi
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalResourceApi::class)
@Composable
fun App() {
    MaterialTheme {
        // Define the items for the bottom navigation
        val items = listOf(
            NavigationItem("Home", Icons.Default.Home), // Replace with actual icon resource
            NavigationItem("Drivers", Icons.Default.AccountCircle), // Replace with actual icon resource
            NavigationItem("Races", Icons.Default.Info) // Replace with actual icon resource
        )

        var selectedItem by remember { mutableStateOf(0) }

        Scaffold(
            bottomBar = {
                BottomNavigation(
                    backgroundColor = Color.Red // Set the background color to red
                ) {
                    items.forEachIndexed { index, item ->
                        BottomNavigationItem(
                            icon = { Icon(imageVector = item.icon, contentDescription = null) },
                            label = { Text(item.title) },
                            selected = selectedItem == index,
                            onClick = { selectedItem = index },
                            selectedContentColor = Color.White, // Set selected icon and text color to white
                            unselectedContentColor = Color.White.copy(alpha = 0.4f) // Set unselected icon and text color to white with some transparency
                        )
                    }
                }
            }
        ) {
            // Based on the selected item, show the corresponding screen
            when (items[selectedItem].title) {
                "Home" -> driverView()
                "Drivers" -> driverView()
                "Races" -> racesView()
            }
        }
    }
}

data class NavigationItem(val title: String, val icon: ImageVector)
