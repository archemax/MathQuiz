package com.ar.mathquiz

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.mathquiz.R
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var button0: Button
    private lateinit var button1: Button
    private lateinit var button2: Button
    private lateinit var button3: Button

    private lateinit var startButton: Button
    private lateinit var resultOfGameTextView: TextView
    private lateinit var linearLayout3: LinearLayout
    private lateinit var resultImageView: ImageView

    private lateinit var scoreTextView: TextView
    private lateinit var correctAnswerTextView: TextView

    private lateinit var mediaPlayer: MediaPlayer

    private var num1 = 0
    private var num2 = 0
    private var maxNumberOfQuestions = 20
    private lateinit var questionTextView: TextView
    private var correctAnswer: Int = 0
    private var totalQuestions: Int = 0
    private var points: Int = 0

    private val answersList = ArrayList<Int>()
    private val random = Random


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button0 = findViewById<Button>(R.id.button0)
        button1 = findViewById<Button>(R.id.button1)
        button2 = findViewById<Button>(R.id.button2)
        button3 = findViewById<Button>(R.id.button3)
        startButton = findViewById<Button>(R.id.startButton)
        resultOfGameTextView = findViewById<TextView>(R.id.resultOfGameTextView)
        resultImageView = findViewById<ImageView>(R.id.resultImageView)
        linearLayout3 = findViewById<LinearLayout>(R.id.linearLayout3)
        scoreTextView = findViewById<TextView>(R.id.scoreTextView)
        correctAnswerTextView = findViewById<TextView>(R.id.correctAnswerTextView)

        questionTextView = findViewById<TextView>(R.id.question)
        mediaPlayer = MediaPlayer.create(this, R.raw.happy_sound)

        // google ads test
        MobileAds.initialize(this) {}
        val mAdView = findViewById<com.google.android.gms.ads.AdView> (R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        startButton.setOnClickListener { startButtonClicked() }
        startGame()
    }

    @SuppressLint("SetTextI18n")
    fun newQuestion() {
        //generate 2 random numbers
        num1 = random.nextInt(0, 5)
        num2 = random.nextInt(0, 6)

        questionTextView.text = "$num1 + $num2"

        correctAnswer = random.nextInt(4)
        answersList.clear()

        for (i in 0 until 4) {
            if (i == correctAnswer) {
                //add the correct answer to the list
                answersList.add(num1 + num2)
            } else {
                var wrongAnswer = random.nextInt(10)
                while (wrongAnswer == (num1 + num2)) {
                    wrongAnswer = random.nextInt(10)
                }
                // add the wrong answer to the list
                answersList.add(wrongAnswer)
            }
        }
        button0.text = answersList[0].toString()
        button1.text = answersList[1].toString()
        button2.text = answersList[2].toString()
        button3.text = answersList[3].toString()

    }

    @SuppressLint("SetTextI18n")
    fun optionSelect(view: View) {


        resultImageView.visibility = View.VISIBLE

        if (correctAnswer == view.tag.toString().toInt()) {
            points++
            totalQuestions++
            playHappySound()
            scoreTextView.text = "total: $totalQuestions"
            correctAnswerTextView.text = "score: $points"
            resultImageView.setImageResource(R.drawable.right_icon)


            if (totalQuestions == maxNumberOfQuestions) {
                endTheGame()
            }

        } else {
            totalQuestions++
            resultImageView.setImageResource(R.drawable.wrong_icon)
            scoreTextView.text = "total: $totalQuestions"
            playSadSound()

            if (totalQuestions == maxNumberOfQuestions) {
                endTheGame()
            }
        }
        newQuestion()


    }


    private fun endTheGame() {


        linearLayout3.visibility = View.INVISIBLE
        button0.visibility = View.INVISIBLE
        button1.visibility = View.INVISIBLE
        button2.visibility = View.INVISIBLE
        button3.visibility = View.INVISIBLE
        scoreTextView.visibility = View.INVISIBLE
        questionTextView.visibility = View.INVISIBLE
        correctAnswerTextView.visibility = View.INVISIBLE

        resultOfGameTextView.visibility = View.VISIBLE
        val myResultOfGame = "your result is: \n$points"
        resultOfGameTextView.text = myResultOfGame

        playAgain()
    }

    @SuppressLint("SetTextI18n")
    fun playAgain() {
        points = 0
        totalQuestions = 0
        scoreTextView.text = "total: $totalQuestions"
        correctAnswerTextView.text = "score: $points"
        startButton.visibility = View.VISIBLE
    }

    @SuppressLint("SetTextI18n")
    private fun startGame() {
        startButton.visibility = View.VISIBLE

        button0.visibility = View.INVISIBLE
        button1.visibility = View.INVISIBLE
        button2.visibility = View.INVISIBLE
        button3.visibility = View.INVISIBLE
        scoreTextView.visibility = View.INVISIBLE
        questionTextView.visibility = View.INVISIBLE
        resultImageView.visibility = View.INVISIBLE
        resultOfGameTextView.visibility = View.INVISIBLE
        correctAnswerTextView.visibility = View.INVISIBLE

        points = 0
        totalQuestions = 0
        scoreTextView.text = "total: $totalQuestions"
        correctAnswerTextView.text = "score: $points"

    }
    //{}

    private fun startButtonClicked() {
        startButton.visibility = View.INVISIBLE
        playHappySound()

        button0.visibility = View.VISIBLE
        button1.visibility = View.VISIBLE
        button2.visibility = View.VISIBLE
        button3.visibility = View.VISIBLE

        scoreTextView.visibility = View.VISIBLE
        correctAnswerTextView.visibility = View.VISIBLE
        questionTextView.visibility = View.VISIBLE
        resultImageView.visibility = View.VISIBLE
        linearLayout3.visibility = View.VISIBLE

        newQuestion()

        resultImageView.visibility = View.INVISIBLE
        resultOfGameTextView.visibility = View.INVISIBLE

    }

    private fun playHappySound() {
        mediaPlayer.release()
        mediaPlayer = MediaPlayer.create(this, R.raw.happy_sound)
        mediaPlayer.start()
    }

    private fun playSadSound() {
        mediaPlayer.release()
        mediaPlayer = MediaPlayer.create(this, R.raw.fart2)
        mediaPlayer.start()
    }


}