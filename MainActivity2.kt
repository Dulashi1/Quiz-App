package com.example.madexam3

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.example.madexam3.databinding.ActivityMain2Binding

class MainActivity2 : AppCompatActivity() {

    // Setup binding
    private lateinit var binding: ActivityMain2Binding

    // Define shared preferences key and context
    private val HIGH_SCORE_KEY = "HIGH_SCORE"
    private lateinit var sharedPreferences: SharedPreferences

    // Define loadHighScore function
    private fun loadHighScore(): Int {
        val sharedPreferences = getSharedPreferences("GAME_DATA", Context.MODE_PRIVATE)
        return sharedPreferences.getInt(HIGH_SCORE_KEY, 0)
    }

    // Define saveHighScore function
    private fun saveHighScore(score: Int) {
        val sharedPreferences = getSharedPreferences("GAME_DATA", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt(HIGH_SCORE_KEY, score)
        editor.apply()
    }

    // Create question array
    private var questions = arrayOf(
        "What is not a correct feature of android studio?",
        "What is not a different technique for data persistence?",
        "What is not a type of  sensor category?",
        "What is not a use of Kotlin?",
        "What is not a type of broadcast receivers?"
    )

    // Create optional array for each question
    private var options = arrayOf(
        arrayOf("Storage", "Web browser", "Slide MangoDB"),
        arrayOf("Internal Storage", "firebase", "Cloud Storage"),
        arrayOf("Motion sensor", "microphone sensor", "position sensor"),
        arrayOf("web development", "Server-side development", "Client-side development"),
        arrayOf("Static", "Dynamic", "Hybrid")
    )

    // Create correct answers for each question
    private val correctAnswers = arrayOf(2, 1, 1, 2, 2)

    // Declare Question Index and score value
    private var currentQuestionIndex = 0
    private var score = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize shared preferences
        sharedPreferences = getSharedPreferences("GAME_DATA", Context.MODE_PRIVATE)

        // Load the high score
        val currentHighScore = loadHighScore()

        // Call displayQuestion() method
        displayQuestion()

        // Call methods and set onclick listeners
        binding.option1Button.setOnClickListener {
            checkAnswer(0)
        }
        binding.option2Button.setOnClickListener {
            checkAnswer(1)
        }
        binding.option3Button.setOnClickListener {
            checkAnswer(2)
        }
        binding.reStartbutton.setOnClickListener {
            restartQuiz()
        }
    }

    // Define function to set correct button colors
    private fun correctButtonColors(buttonIndex: Int) {
        when (buttonIndex) {
            0 -> binding.option1Button.setBackgroundColor(Color.GREEN)
            1 -> binding.option2Button.setBackgroundColor(Color.GREEN)
            2 -> binding.option3Button.setBackgroundColor(Color.GREEN)
        }
    }

    // Define function to set wrong button colors
    private fun wrongButtonColors(buttonIndex: Int) {
        when (buttonIndex) {
            0 -> binding.option1Button.setBackgroundColor(Color.RED)
            1 -> binding.option2Button.setBackgroundColor(Color.RED)
            2 -> binding.option3Button.setBackgroundColor(Color.RED)
        }
    }

    // Define function to reset button colors
    private fun resetButtonColors() {
        binding.option1Button.setBackgroundColor(Color.rgb(50, 59, 100))
        binding.option2Button.setBackgroundColor(Color.rgb(50, 59, 100))
        binding.option3Button.setBackgroundColor(Color.rgb(50, 59, 100))
    }

    // Define function to show quiz results
    private fun showResults() {
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setTitle("Quiz Results")
        dialogBuilder.setMessage("Your Score: $score out of ${questions.size}")
        dialogBuilder.setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss()
            binding.reStartbutton.isEnabled = true
        }
        val dialog = dialogBuilder.create()
        dialog.show()

        // Save the user's score in SharedPreferences
        saveHighScore(score)
    }

    // Define function to display current question
    private fun displayQuestion() {
        binding.questionNumber.text = questions[currentQuestionIndex]
        binding.option1Button.text = options[currentQuestionIndex][0]
        binding.option2Button.text = options[currentQuestionIndex][1]
        binding.option3Button.text = options[currentQuestionIndex][2]
        resetButtonColors()
    }

    // Define function to check answer
    private fun checkAnswer(selectedAnswerIndex: Int) {
        val correctAnswerIndex = correctAnswers[currentQuestionIndex]
        if (selectedAnswerIndex == correctAnswerIndex) {
            score++
            correctButtonColors(selectedAnswerIndex)
        } else {
            wrongButtonColors(selectedAnswerIndex)
            correctButtonColors(correctAnswerIndex)
        }
        if (currentQuestionIndex < questions.size - 1) {
            currentQuestionIndex++
            binding.questionNumber.postDelayed({ displayQuestion() }, 1000)
        } else {
            showResults()
        }
    }

    // Define function to restart quiz
    private fun restartQuiz() {
        currentQuestionIndex = 0
        score = 0
        displayQuestion()
        binding.reStartbutton.isEnabled = false
    }
}
