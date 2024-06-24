package com.example.fourfragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment

class FragmentRouge : Fragment() {

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
        val view = inflater.inflate(R.layout.fragment_rouge, container, false)
        view.setOnClickListener {
            listener?.onColorClick(1)
        }
        return view
    }

    fun flash() {
        val animation = AnimationUtils.loadAnimation(context, R.anim.flash)
        view?.startAnimation(animation)
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }
}
