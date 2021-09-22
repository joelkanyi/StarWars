package com.kanyideveloper.starwars.ui

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.kanyideveloper.starwars.R

class SplashFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        Handler().postDelayed(Runnable {
            findNavController().navigate(R.id.action_splashFragment_to_charactersFragment)
        }, 3000)

        return inflater.inflate(R.layout.fragment_splash, container, false)
    }
}