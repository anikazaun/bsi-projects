package com.bsi.conversion_app.Composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onConvertClick: (inputValue: String, selectedUnit: String) -> Unit
) {
    val units = listOf("m²", "Jahre", "€")

    var selectedUnit by remember { mutableStateOf(units[0]) }
    var inputValue by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Box(
        modifier = modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            InputRow(
                inputValue = inputValue,
                onInputChange = {
                    inputValue = it
                    errorMessage = null // Fehler zurücksetzen bei Eingabeänderung
                },
                selectedUnit = selectedUnit,
                onUnitSelected = { selectedUnit = it },
                expanded = expanded,
                onExpandChange = { expanded = it },
                units = units
            )

            if (errorMessage != null) {
                Text(
                    text = errorMessage ?: "",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }

        ConvertButton(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(24.dp)
                .fillMaxWidth()
                .height(48.dp),
            text = "Convert",
            onClick = {
                if (inputValue.toDoubleOrNull() != null && inputValue.isNotBlank()) {
                    errorMessage = null
                    onConvertClick(inputValue, selectedUnit)
                } else {
                    errorMessage = "Bitte gib eine gültige Zahl ein."
                }
            }
        )
    }
}

