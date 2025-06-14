package com.bsi.conversion_app.Pages

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bsi.conversion_app.Composables.BulletPoint
import com.bsi.conversion_app.Composables.ConvertButton
import androidx.compose.ui.Alignment

fun formatDynamic(value: Double): String {
    // Je nach Wertgröße bis zu 5 Nachkommastellen, sonst weniger
    return when {
        value < 0.0001 -> "%.5e".format(value) // sehr kleine Zahlen im wissenschaftlichen Format
        value < 1 -> "%.5f".format(value).trimEnd('0').trimEnd('.') // bis 5 Nachkommastellen, trailing zeros weg
        value < 10 -> "%.4f".format(value).trimEnd('0').trimEnd('.')
        value < 100 -> "%.3f".format(value).trimEnd('0').trimEnd('.')
        value < 1000 -> "%.2f".format(value).trimEnd('0').trimEnd('.')
        else -> "%.1f".format(value) // große Zahlen nur 1 Nachkommastelle
    }
}

fun safeDivide(a: Double, b: Double): Double {
    return if (b == 0.0) Double.NaN else a / b
}

fun convertValue(inputValue: Double, unit: String): List<String> {
    return when (unit) {
        "m²" -> {
            val fussballfelder = safeDivide(inputValue, 7140.0)
            val tennisplaetze = safeDivide(inputValue, 194.0)
            val zentralePark = 3_410_000.0 // m² Fläche Central Park NYC
            listOf(
                "...${formatDynamic(fussballfelder)} Fußballfelder",
                "...${formatDynamic(tennisplaetze)} Tennisplätze",
                "...${formatDynamic(safeDivide(inputValue, zentralePark) * 100)}% der Fläche des Central Parks"
            )
        }
        "Jahre" -> {
            val minuten = inputValue * 365 * 24 * 60
            val sekunden = minuten * 60
            val erdeAlter = 4_540_000_000.0 // ca. 4,54 Mrd Jahre
            listOf(
                "...${formatDynamic(minuten)} Minuten",
                "...${formatDynamic(sekunden)} Sekunden",
                "...${formatDynamic(inputValue / erdeAlter * 100)} Prozent vom Alter der Erde"
            )
        }
        "€" -> {
            val minuten = safeDivide(inputValue*60, 12.82) // (inputValue / 60) / 12
            val brötchen = safeDivide(inputValue, 0.55)   // 55 Cent pro Brötchen
            val villen = safeDivide(inputValue, 800_000.0)

            listOf(
                "...${formatDynamic(minuten)} Minuten Arbeitszeit gegen Mindestlohn",
                "...${formatDynamic(brötchen)} Brötchen",
                "...${formatDynamic(villen)} Villen in Deutschland"
            )
        }
        else -> listOf("Keine Umrechnung verfügbar für $unit")
    }
}

@Composable
fun AnswerScreen(
    valueWithUnit: String,
    onNewConversionClick: () -> Unit
) {
    val parts = valueWithUnit.trim().split(" ")
    val inputStr = parts.getOrNull(0) ?: ""
    val unit = parts.getOrNull(1) ?: ""

    val inputValue = inputStr.toDoubleOrNull()

    val bulletPoints = if (inputValue == null) {
        listOf("Fehler: Bitte gib eine gültige Zahl ein.")
    } else {
        convertValue(inputValue, unit)
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = if (inputValue == null) "Ungültige Eingabe" else "$valueWithUnit, Das sind...",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                items(bulletPoints) { point ->
                    BulletPoint(text = point)
                }
            }
        }

        ConvertButton(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(24.dp)
                .height(48.dp),
            text = "New Conversion",
            onClick = onNewConversionClick
        )
    }
}
