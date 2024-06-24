package com.example.fourfragments

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class FragmentJaune : Fragment() {

    private var listener: OnColorClickListener? = null

    interface OnColorClickListener {
        fun onColorClick(colorIndex: Int)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnColorClickListener) {
            listener = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_jaune, container, false)
        view.setOnClickListener {
            listener?.onColorClick(0)
        }
        return view
    }

    fun flash() {
        view?.setBackgroundColor(Color.WHITE)
        Handler(Looper.getMainLooper()).postDelayed({
            view?.setBackgroundColor(resources.getColor(R.color.holo_orange_light, null))
        }, 500)
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }
}
