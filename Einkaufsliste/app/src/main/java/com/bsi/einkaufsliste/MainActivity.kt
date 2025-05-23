package com.bsi.einkaufsliste

// Diese Klassen brauchen wir für unsere App:
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge

// Diese Imports sind für das UI (Benutzeroberfläche) mit Jetpack Compose:
import androidx.compose.foundation.clickable // Für "Artikel antippen"
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bsi.einkaufsliste.ui.theme.EinkaufslisteTheme

// MainActivity ist der Einstiegspunkt der App
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Diese Funktion sorgt für ein Layout, das bis an den Bildschirmrand geht
        enableEdgeToEdge()

        // Hier sagen wir: Dieses UI soll angezeigt werden
        setContent {
            EinkaufslisteTheme { // Unser App-Design (Farben, Schriften, etc.)

                // Scaffold stellt eine Grundstruktur für das Layout zur Verfügung
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    // `text` ist das, was im Textfeld steht (Benutzereingabe)
                    var text by remember { mutableStateOf("") }

                    // `items` ist die Liste der hinzugefügten Artikel
                    val items = remember { mutableStateListOf<String>() }

                    // Column ordnet die UI-Elemente untereinander an
                    Column(modifier = Modifier.padding(innerPadding)) {

                        // TextField für die Benutzereingabe
                        TextField(
                            value = text, // Aktueller Text im Feld
                            onValueChange = { newText -> text = newText }, // Wird aufgerufen, wenn der Benutzer etwas eintippt
                            label = { Text("Artikel eingeben") } // Kleiner Hinweistext im Feld
                        )

                        // Button zum Hinzufügen eines Artikels zur Liste
                        Button(
                            onClick = {
                                // Nur hinzufügen, wenn das Textfeld nicht leer ist
                                if (text.isNotBlank()) {
                                    items.add(text) // Artikel zur Liste hinzufügen
                                    text = ""       // Textfeld danach leeren
                                }
                            }
                        ) {
                            Text("Hinzufügen") // Text im Button
                        }

                        // Hier zeigen wir alle Artikel in der Liste an
                        for (item in items) {
                            Text(
                                text = item, // Artikelname
                                modifier = Modifier
                                    .padding(8.dp) // Etwas Abstand nach außen
                                    .clickable {
                                        items.remove(item) // Artikel aus Liste entfernen, wenn man drauf klickt
                                    }
                            )
                        }
                    }
                }
            }
        }
    }
}
