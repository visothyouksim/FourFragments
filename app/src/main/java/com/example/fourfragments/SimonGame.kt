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

    // Liste pour stocker la séquence de couleurs générée par le jeu
    private val sequence = mutableListOf<Int>()
    // Liste pour stocker la séquence de couleurs entrée par l'utilisateur
    private var userSequence = mutableListOf<Int>()
    // Générateur de nombres aléatoires
    private val random = Random
    // Handler pour gérer les délais
    private val handler = Handler(Looper.getMainLooper())

    // Références aux fragments de couleur
    private lateinit var fragmentVert: FragmentVert
    private lateinit var fragmentRouge: FragmentRouge
    private lateinit var fragmentBleu: FragmentBleu
    private lateinit var fragmentJaune: FragmentJaune

    // Score du joueur
    private var score = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.simon_game)

        // Gestionnaire de fragments
        val fragmentManager: FragmentManager = supportFragmentManager
        // Transaction de fragments
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()

        // Initialisation des fragments
        fragmentVert = FragmentVert()
        fragmentRouge = FragmentRouge()
        fragmentBleu = FragmentBleu()
        fragmentJaune = FragmentJaune()

        // Ajout des fragments à l'activité
        fragmentTransaction.add(R.id.fragment_container_vert, fragmentVert)
        fragmentTransaction.add(R.id.fragment_container_rouge, fragmentRouge)
        fragmentTransaction.add(R.id.fragment_container_bleu, fragmentBleu)
        fragmentTransaction.add(R.id.fragment_container_jaune, fragmentJaune)

        // Validation de la transaction
        fragmentTransaction.commit()

        // Démarrage du jeu
        startGame()
    }

    // Fonction pour démarrer le jeu
    private fun startGame() {
        score = 0 // Réinitialisation du score
        sequence.clear() // Réinitialisation de la séquence de jeu
        userSequence.clear() // Réinitialisation de la séquence de l'utilisateur
        addNewColorToSequence() // Ajout d'une nouvelle couleur à la séquence
        playSequence() // Lecture de la séquence
        updateScoreDisplay() // Mise à jour de l'affichage du score
    }

    // Fonction pour ajouter une nouvelle couleur à la séquence
    private fun addNewColorToSequence() {
        sequence.add(random.nextInt(4)) // Ajout d'une couleur aléatoire (0 à 3) à la séquence
    }

    // Fonction pour lire la séquence de couleurs
    private fun playSequence() {
        userSequence.clear() // Réinitialisation de la séquence de l'utilisateur
        for (i in sequence.indices) {
            handler.postDelayed({
                flashColor(sequence[i]) // Déclenche le flash de la couleur avec un délai
            }, (i * 1000).toLong())
        }
    }

    // Fonction pour faire clignoter la couleur correspondant à l'index donné
    private fun flashColor(colorIndex: Int) {
        when (colorIndex) {
            0 -> fragmentVert.flash() // Clignote le fragment vert
            1 -> fragmentRouge.flash() // Clignote le fragment rouge
            2 -> fragmentBleu.flash() // Clignote le fragment bleu
            3 -> fragmentJaune.flash() // Clignote le fragment jaune
        }
    }

    // Fonction appelée lorsque l'utilisateur clique sur une couleur
    override fun onColorClick(colorIndex: Int) {
        userSequence.add(colorIndex) // Ajoute la couleur cliquée par l'utilisateur à sa séquence
        if (userSequence[userSequence.size - 1] != sequence[userSequence.size - 1]) {
            endGame() // Termine le jeu si la couleur cliquée est incorrecte
        } else if (userSequence.size == sequence.size) {
            score++ // Incrémente le score si la séquence de l'utilisateur est complète et correcte
            updateScoreDisplay() // Met à jour l'affichage du score
            showSuccessMessage() // Affiche un message de succès
            handler.postDelayed({
                addNewColorToSequence() // Ajoute une nouvelle couleur à la séquence
                playSequence() // Lit la nouvelle séquence
            }, 1000) // Délai avant de commencer la nouvelle séquence
        }
    }

    // Fonction pour afficher un message de succès
    private fun showSuccessMessage() {
        Snackbar.make(findViewById(android.R.id.content), "Bien joué!", Snackbar.LENGTH_SHORT).show()
    }

    // Fonction pour afficher un message d'erreur
    private fun showErrorMessage() {
        Snackbar.make(findViewById(android.R.id.content), "Mauvaise réponse. Essayez encore.", Snackbar.LENGTH_SHORT).show()
    }

    // Fonction pour terminer le jeu
    private fun endGame() {
        showErrorMessage() // Affiche un message d'erreur
        handler.postDelayed({ startGame() }, 2000) // Redémarre le jeu après un délai
    }

    // Fonction pour mettre à jour l'affichage du score
    private fun updateScoreDisplay() {
        findViewById<TextView>(R.id.score_text_view).text = "Score: $score"
    }
}
