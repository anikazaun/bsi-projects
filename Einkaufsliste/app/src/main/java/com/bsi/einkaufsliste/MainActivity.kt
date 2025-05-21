package com.bsi.einkaufsliste

// Klassen die wir für unsere App brauchen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge

// Imports für unsere Benutzeroberfläche
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.bsi.einkaufsliste.ui.theme.EinkaufslisteTheme
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EinkaufslisteTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    var text by remember { mutableStateOf("") }
                    val items = remember { mutableStateListOf<String>() }

                    Column(modifier = Modifier.padding(innerPadding)) {
                        TextField(
                            value = text,
                            onValueChange = { newText -> text = newText },
                            label = { Text("Artikel eingeben") }
                        )

                        Button(
                            onClick = {
                                if (text.isNotBlank()) {
                                    items.add(text)
                                    text = "" // Eingabefeld leeren
                                }
                            }
                        ) {
                            Text("Hinzufügen")
                        }

                        // Liste anzeigen
                        for (item in items) {
                            Text(
                                text = item,
                                modifier = Modifier
                                    .padding(8.dp)
                                    .clickable {
                                        items.remove(item)
                                    }
                            )
                        }

                    }
                }

            }
            }
        }
    }


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    EinkaufslisteTheme {
        Greeting("Android")
    }
}