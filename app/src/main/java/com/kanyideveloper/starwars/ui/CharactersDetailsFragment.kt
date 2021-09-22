package com.kanyideveloper.starwars.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.kanyideveloper.starwars.adapters.FilmsAdapter
import com.kanyideveloper.starwars.databinding.FragmentCharactersDetailsBinding
import com.kanyideveloper.starwars.utils.Resource
import com.kanyideveloper.starwars.viewmodels.CharacterDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

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
    ): View {
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

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.filmResponseDetails.collect { event ->
                when (event) {
                    is Resource.Success -> {
                        binding.filmProgressBar.isVisible = false
                        filmsAdapter.submitList(event.data)
                        binding.recyclerViewFilms.adapter = filmsAdapter
                    }
                    is Resource.Failure -> {
                        binding.filmProgressBar.isVisible = false
                        binding.textViewFilmsError.isVisible = true
                        binding.textViewFilmsError.text = event.message
                    }
                    is Resource.Loading -> {
                        binding.filmProgressBar.isVisible = true
                    }
                    else -> Unit
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.homeWorldResponse.collect { event ->
                when (event) {
                    is Resource.Success -> {
                        binding.progressBarHomeWord.isVisible = false
                        binding.textViewHomeWorldValue.text = event.data!!.name
                    }
                    is Resource.Failure -> {
                        binding.progressBarHomeWord.isVisible = false
                        binding.textViewHomeWorldValue.text = event.message
                        Toast.makeText(requireContext(), event.message, Toast.LENGTH_SHORT).show()
                    }
                    is Resource.Loading -> {
                        binding.progressBarHomeWord.isVisible = true
                    }
                    else -> Unit
                }
            }
        }

        return view
    }
}