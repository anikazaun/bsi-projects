package com.bsi.workoutapp

import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bsi.workoutapp.ui.theme.WorkoutAppTheme
import com.bsi.workoutapp.R
import androidx.navigation.compose.*

class MainActivity : ComponentActivity() {

    private val viewModel: WorkoutViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            WorkoutAppTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    WorkoutNavigation(viewModel)
                }
            }
        }
    }

    // Funktion zum Abspielen von Sound
    fun playSound(resId: Int) {
        val player = MediaPlayer.create(this, resId)
        player.setOnCompletionListener {
            it.release()
        }
        player.start()
    }
}

@Composable
fun WorkoutNavigation(viewModel: WorkoutViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "start") {
        composable("start") {
            StartScreen(onStartClick = { navController.navigate("workout") })
        }
        composable("workout") {
            WorkoutScreen(viewModel)
        }
        composable("statistik") {
            StatistikScreen()
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

            Button(onClick = { viewModel.startPause(30) }) {
                Text("Pause starten")
            }
        }
    }

    // Ton abspielen, wenn Trigger aktiv
    if (viewModel.playSoundTrigger) {
        (LocalContext.current as? MainActivity)?.playSound(R.raw.bleep)
        viewModel.playSoundTrigger = false
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewWorkoutScreen() {
    WorkoutAppTheme {
        WorkoutScreen(viewModel = WorkoutViewModel())
    }
}
