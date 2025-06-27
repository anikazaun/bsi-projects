package com.bsi.workoutapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bsi.workoutapp.ui.theme.WorkoutAppTheme

class MainActivity : ComponentActivity() {

    // ViewModel wird hier erstellt (viewModels ist eine ViewModel-Funktion von Android)
    private val viewModel: WorkoutViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WorkoutAppTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    WorkoutScreen(viewModel)
                }
            }
        }
    }
}

@Composable
fun WorkoutScreen(viewModel: WorkoutViewModel) {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        Text(text = "Aktuelle Übung: ${viewModel.currentExercise}", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = { viewModel.nextExercise() }) {
            Text("Nächste Übung")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewWorkoutScreen() {
    WorkoutAppTheme {
        WorkoutScreen(viewModel = WorkoutViewModel())
    }
}
