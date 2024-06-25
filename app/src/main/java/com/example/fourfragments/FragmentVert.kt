package com.example.fourfragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment

class FragmentVert : Fragment() {

    // Déclaration de l'interface pour écouter les clics sur les couleurs
    private var listener: OnColorClickListener? = null

    // Interface pour gérer les clics sur les couleurs
    interface OnColorClickListener {
        fun onColorClick(colorIndex: Int)
    }

    // Méthode appelée lors de l'attachement du fragment à son contexte (activité)
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnColorClickListener) {
            listener = context // Associe le listener de clic de couleur au contexte si celui-ci implémente l'interface
        }
    }

    // Méthode appelée pour créer la vue du fragment
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate le layout pour ce fragment
        val view = inflater.inflate(R.layout.fragment_vert, container, false)
        // Définit un écouteur de clic sur la vue
        view.setOnClickListener {
            listener?.onColorClick(0) // Informe le listener qu'une couleur a été cliquée
        }
        return view
    }

    // Méthode pour faire clignoter la couleur
    fun flash() {
        val animation = AnimationUtils.loadAnimation(context, R.anim.flash)
        view?.startAnimation(animation) // Démarre l'animation de flash sur la vue du fragment
    }

    // Méthode appelée lors du détachement du fragment de son contexte
    override fun onDetach() {
        super.onDetach()
        listener = null // Déconnecte le listener pour éviter les fuites de mémoire
    }
}
