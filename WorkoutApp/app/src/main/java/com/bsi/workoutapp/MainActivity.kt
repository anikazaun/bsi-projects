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
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*



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
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        if (viewModel.isInPause) {
            // Pause wird angezeigt
            Text(
                text = "Pause",
                style = MaterialTheme.typography.headlineLarge
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Nächste Übung in: ${viewModel.countdownTime} Sekunden",
                style = MaterialTheme.typography.bodyLarge
            )
        } else {
            // Normale Übung wird angezeigt
            Text(
                text = viewModel.currentExercise.name,
                style = MaterialTheme.typography.headlineLarge
            )

            Spacer(modifier = Modifier.height(12.dp))

            Image(
                painter = painterResource(id = viewModel.currentExercise.imageResId),
                contentDescription = viewModel.currentExercise.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = viewModel.currentExercise.description,
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(onClick = { viewModel.startPause(10) }) {
                Text("Pause starten")
            }
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
