import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.bsi.conversion_app.Composables.HomeScreen
import com.bsi.conversion_app.Pages.AnswerScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConversionApp() {
    var currentScreen by remember { mutableStateOf("home") }
    var inputValue by remember { mutableStateOf("") }
    var selectedUnit by remember { mutableStateOf("m²") }

    Scaffold(
        topBar = { AppHeader() }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (currentScreen) {
                "home" -> HomeScreen(
                    modifier = Modifier.fillMaxSize(),
                    onConvertClick = { value, unit ->
                        inputValue = value
                        selectedUnit = unit
                        currentScreen = "answer"
                    }
                )
                "answer" -> AnswerScreen(
                    valueWithUnit = "$inputValue $selectedUnit",
                    onNewConversionClick = { currentScreen = "home" }
                )
            }
        }
    }
}
