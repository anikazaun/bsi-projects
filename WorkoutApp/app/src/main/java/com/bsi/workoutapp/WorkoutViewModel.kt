package com.bsi.workoutapp

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel

class WorkoutViewModel : ViewModel() {

    private val exerciseList = listOf(
        Exercise("Squat", "Beuge deine Knie, halte Rücken gerade.", R.drawable.squat),
        Exercise("Lunge", "Mache einen großen Schritt nach vorn und senke den Körper.", R.drawable.lunges),
        Exercise("Plank", "Halte deinen Körper wie ein Brett gestützt auf Unterarmen.", R.drawable.plank),
        Exercise("Crunch", "Ziehe Oberkörper kontrolliert Richtung Knie.", R.drawable.crunch),
        Exercise("Superman", "Hebe Arme und Beine gleichzeitig im Liegen.", R.drawable.superman),
        Exercise("Jumping Jack", "Springe mit Armen und Beinen gleichzeitig auseinander.", R.drawable.jumpingjack),
        Exercise("Push-up", "Senke deinen Körper mit geradem Rücken und drücke dich wieder hoch.", R.drawable.pushup),
        Exercise("Glute Bridge", "Hebe dein Becken nach oben aus Rückenlage.", R.drawable.glutebridge)
    )

    private var currentIndex by mutableStateOf(0)

    val currentExercise: Exercise
        get() = exerciseList[currentIndex]

    fun nextExercise() {
        if (currentIndex < exerciseList.size - 1) {
            currentIndex++
        } else {
            currentIndex = 0 // zurück zum Start, wenn fertig
        }
    }
}
