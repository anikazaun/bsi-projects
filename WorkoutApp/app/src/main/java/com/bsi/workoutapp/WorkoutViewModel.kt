package com.bsi.workoutapp

import android.os.CountDownTimer
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.bsi.workoutapp.R

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

    var isInPause by mutableStateOf(false)
        private set

    var countdownTime by mutableStateOf(0)
        private set

    private var timer: CountDownTimer? = null

    fun nextExercise() {
        if (currentIndex < exerciseList.size - 1) {
            currentIndex++
        } else {
            currentIndex = 0
        }
    }

    fun startPause(durationInSeconds: Int = 10) {
        isInPause = true
        countdownTime = durationInSeconds

        timer?.cancel()
        timer = object : CountDownTimer(durationInSeconds * 1000L, 1000L) {
            override fun onTick(millisUntilFinished: Long) {
                countdownTime = (millisUntilFinished / 1000).toInt()
            }

            override fun onFinish() {
                isInPause = false
                nextExercise()
            }
        }.start()
    }

    override fun onCleared() {
        super.onCleared()
        timer?.cancel()
    }
}
