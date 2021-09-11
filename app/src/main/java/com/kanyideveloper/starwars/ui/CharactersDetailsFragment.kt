package com.kanyideveloper.starwars.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.kanyideveloper.starwars.adapters.FilmsAdapter
import com.kanyideveloper.starwars.databinding.FragmentCharactersDetailsBinding
import com.kanyideveloper.starwars.viewmodels.CharacterDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharactersDetailsFragment : Fragment() {

    private lateinit var binding: FragmentCharactersDetailsBinding
    private val viewModel: CharacterDetailsViewModel by viewModels()
    private val filmsAdapter: FilmsAdapter by lazy {
        FilmsAdapter()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCharactersDetailsBinding.inflate(inflater, container, false)
        val view = binding.root

        viewModel.details.observe(viewLifecycleOwner, Observer { result ->
            binding.textViewFullNameValue.text = result.name
            binding.textViewSkinColorValue.text = result.skinColor
            binding.textViewHairColorValue.text = result.hairColor
            binding.textViewHeightValue.text = result.height
            binding.textViewMassValue.text = result.mass
            binding.textViewEyeColorValue.text = result.eyeColor
            binding.textViewGenderValue.text = result.gender
            binding.textViewBirthYearValue.text = result.birthYear
        })

        viewModel.filmDetails.observe(viewLifecycleOwner, Observer { films ->
            filmsAdapter.submitList(films)
            binding.recyclerViewFilms.adapter = filmsAdapter
        })

        viewModel.homeWorld.observe(viewLifecycleOwner, Observer { homeWorld ->
            binding.textViewHomeWorldValue.text = homeWorld.name
        })

        return view
    }
}