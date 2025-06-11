package com.bsi.quiz_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bsi.quiz_app.ui.theme.QuizAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // Inhalt bis unter Status-/Navigation-Bar anzeigen
        setContent {
            QuizAppTheme { // Hier wird das Theme der App angewendet
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    QuizApp() // Hauptinhalt der App (Composable-Funktion)
                }
            }
        }
    }
}

data class Question(
    val questionText: String, // Die Frage selbst
    val answers: List<String>, // Mögliche Antworten
    val correctAnswer: String // Richtige Antwort
)

@Composable
fun QuizApp() {
    // Fragenliste
    val questions = listOf(
        Question(
            "Was ist die Hauptstadt von Deutschland?",
            listOf("Berlin", "München", "Hamburg"),
            "Berlin"
        ),
        Question(
            "Welche Farbe entsteht, wenn man Blau und Gelb mischt?",
            listOf("Grün", "Lila", "Orange"),
            "Grün"
        ),
        Question(
            "Wie viele Kontinente gibt es?",
            listOf("5", "7", "6"),
            "7"
        )
    )

    // Zustände: Startbildschirm, aktuelle Frage, Punktestand, Endbildschirm
    var quizStarted by remember { mutableStateOf(false) }
    var currentQuestionIndex by remember { mutableStateOf(0) }
    var score by remember { mutableStateOf(0) }
    var showEndScreen by remember { mutableStateOf(false) }

    // Startbildschirm
    if (!quizStarted) {
        // Startbildschirm
        StartScreen(onStartQuiz = {
            quizStarted = true
            currentQuestionIndex = 0
            score = 0
            showEndScreen = false
        })
    // Endbildschirm
    } else if (showEndScreen) {
        EndScreen(score = score, totalQuestions = questions.size) {
            // Quiz neu starten
            quizStarted = false
        }
    // Fragenbildschirm
    } else {
        QuizScreen(
            question = questions[currentQuestionIndex],
            onAnswerSelected = { isCorrect ->
                if (isCorrect) score++
            },
            onNextQuestion = {
                if (currentQuestionIndex + 1 < questions.size) {
                    currentQuestionIndex++
                } else {
                    showEndScreen = true
                }
            },
            score = score
        )
    }
}
// Startbildschirm
@Composable
fun StartScreen(onStartQuiz: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Willkommen zum Quiz!",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 24.dp)
        )
        Button(onClick = onStartQuiz) {
            Text(text = "Quiz starten")
        }
    }
}

// Quiz Screen
@Composable
fun QuizScreen(
    question: Question,
    onAnswerSelected: (Boolean) -> Unit,
    onNextQuestion: () -> Unit,
    score: Int
) {
    // Zustände für die Auswahl und Bewertung
    var selectedAnswer by remember { mutableStateOf<String?>(null) }
    var answerChecked by remember { mutableStateOf(false) }
    var isAnswerCorrect by remember { mutableStateOf<Boolean?>(null) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Hauptinhalt oben
        Column(
            modifier = Modifier.align(Alignment.TopCenter),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = question.questionText,
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(bottom = 24.dp)
            )
            // Antwortr Buttons
            question.answers.forEach { answer ->
                val isSelected = answer == selectedAnswer
                val isCorrectAnswer = answer.trim().equals(question.correctAnswer.trim(), ignoreCase = true)
            // Hintergrundfarbe je nach Zustand
                val backgroundColor = when {
                    !answerChecked -> MaterialTheme.colorScheme.primary
                    isSelected && isCorrectAnswer -> Color(0xFF4CAF50)
                    isSelected && !isCorrectAnswer -> Color(0xFFF44336)
                    else -> MaterialTheme.colorScheme.primary
                }

                Button(
                    onClick = {
                        if (!answerChecked) {
                            selectedAnswer = answer
                            val correct = isCorrectAnswer
                            isAnswerCorrect = correct
                            onAnswerSelected(correct)
                            answerChecked = true
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = backgroundColor),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    shape = RoundedCornerShape(8.dp),
                    enabled = !answerChecked
                ) {
                    Text(text = answer, fontSize = 18.sp)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            // Feedback + Nächste Frage Button
            if (answerChecked) {
                val feedbackText = if (isAnswerCorrect == true) {
                    "Richtig!"
                } else {
                    "Falsch! Die richtige Antwort war: ${question.correctAnswer}"
                }

                Text(
                    text = feedbackText,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (isAnswerCorrect == true) Color(0xFF4CAF50) else Color(0xFFF44336),
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Button(
                    onClick = {
                        selectedAnswer = null
                        answerChecked = false
                        isAnswerCorrect = null
                        onNextQuestion()
                    }
                ) {
                    Text(text = "Nächste Frage")
                }
            }

        }

        // Punktestand unten
        Text(
            text = "Punktestand: $score",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 24.dp)
        )
    }
}

// Endbildschirm
@Composable
fun EndScreen(score: Int, totalQuestions: Int, onRestart: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Quiz beendet!",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Text(
            text = "Du hast $score von $totalQuestions richtig!",
            fontSize = 24.sp,
            modifier = Modifier.padding(bottom = 32.dp)
        )
        Button(onClick = onRestart) {
            Text(text = "Quiz neu starten")
        }
    }
}

