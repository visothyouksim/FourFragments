package com.example.fourfragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.snackbar.Snackbar
import kotlin.random.Random

class SimonGame : AppCompatActivity(), FragmentVert.OnColorClickListener, FragmentRouge.OnColorClickListener,
    FragmentBleu.OnColorClickListener, FragmentJaune.OnColorClickListener {

    private val sequence = mutableListOf<Int>()
    private var userSequence = mutableListOf<Int>()
    private val random = Random
    private val handler = Handler(Looper.getMainLooper())

    private lateinit var fragmentVert: FragmentVert
    private lateinit var fragmentRouge: FragmentRouge
    private lateinit var fragmentBleu: FragmentBleu
    private lateinit var fragmentJaune: FragmentJaune

    private var score = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.simon_game)

        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()

        fragmentVert = FragmentVert()
        fragmentRouge = FragmentRouge()
        fragmentBleu = FragmentBleu()
        fragmentJaune = FragmentJaune()

        fragmentTransaction.add(R.id.fragment_container_vert, fragmentVert)
        fragmentTransaction.add(R.id.fragment_container_rouge, fragmentRouge)
        fragmentTransaction.add(R.id.fragment_container_bleu, fragmentBleu)
        fragmentTransaction.add(R.id.fragment_container_jaune, fragmentJaune)

        fragmentTransaction.commit()

        startGame()
    }

    private fun startGame() {
        score = 0
        sequence.clear()
        userSequence.clear()
        addNewColorToSequence()
        playSequence()
        updateScoreDisplay()
    }

    private fun addNewColorToSequence() {
        sequence.add(random.nextInt(4))
    }

    private fun playSequence() {
        userSequence.clear()
        for (i in sequence.indices) {
            handler.postDelayed({
                flashColor(sequence[i])
            }, (i * 1000).toLong())
        }
    }

    private fun flashColor(colorIndex: Int) {
        when (colorIndex) {
            0 -> fragmentVert.flash()
            1 -> fragmentRouge.flash()
            2 -> fragmentBleu.flash()
            3 -> fragmentJaune.flash()
        }
    }

    override fun onColorClick(colorIndex: Int) {
        userSequence.add(colorIndex)
        if (userSequence[userSequence.size - 1] != sequence[userSequence.size - 1]) {
            endGame()
        } else if (userSequence.size == sequence.size) {
            score++
            updateScoreDisplay()
            showSuccessMessage()
            handler.postDelayed({
                addNewColorToSequence()
                playSequence()
            }, 1000)
        }
    }

    private fun showSuccessMessage() {
        Snackbar.make(findViewById(android.R.id.content), "Bien joué!", Snackbar.LENGTH_SHORT).show()
    }

    private fun showErrorMessage() {
        Snackbar.make(findViewById(android.R.id.content), "Mauvaise réponse. Essayez encore.", Snackbar.LENGTH_SHORT).show()
    }

    private fun endGame() {
        showErrorMessage()
        handler.postDelayed({ startGame() }, 2000)
    }

    private fun updateScoreDisplay() {
        findViewById<TextView>(R.id.score_text_view).text = "Score: $score"
    }
}
