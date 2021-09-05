package com.kanyideveloper.starwars.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.kanyideveloper.starwars.R
import com.kanyideveloper.starwars.databinding.FragmentCharactersDetailsBinding
import com.kanyideveloper.starwars.viewmodels.CharacterDetailsViewModel
import com.kanyideveloper.starwars.viewmodels.CharacterDetailsViewModelFactory
import timber.log.Timber

class CharactersDetailsFragment : Fragment() {

    private lateinit var binding: FragmentCharactersDetailsBinding
    private lateinit var viewModel: CharacterDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCharactersDetailsBinding.inflate(inflater, container, false)
        val view = binding.root

        val application = requireNotNull(activity).application
        val characterDetails =
            CharactersDetailsFragmentArgs.fromBundle(requireArguments()).characterDetails
        val viewModelFactory = CharacterDetailsViewModelFactory(characterDetails, application)
        viewModel =
            ViewModelProvider(this, viewModelFactory).get(CharacterDetailsViewModel::class.java)

        viewModel.details.observe(viewLifecycleOwner, Observer { result ->
            binding.textViewFullNameValue.text = result.name
            binding.textViewSkinColorValue.text = result.skinColor
            binding.textViewHairColorValue.text = result.hairColor
            binding.textViewHeightValue.text = result.height
            binding.textViewMassValue.text = result.mass
            binding.textViewEyeColorValue.text = result.eyeColor
            //binding.textViewHomeWorldValue.text = args.value.characterDetails.
            Timber.d("${result.films}")

            binding.textViewGenderValue.text = result.gender
            binding.textViewBirthYearValue.text = result.birthYear
        })

        return view
    }
}