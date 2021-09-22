package com.kanyideveloper.starwars.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.kanyideveloper.starwars.adapters.CharactersAdapter
import com.kanyideveloper.starwars.databinding.FragmentCharactersBinding
import com.kanyideveloper.starwars.utils.hideKeyboard
import com.kanyideveloper.starwars.viewmodels.CharactersViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect


@AndroidEntryPoint
class CharactersFragment : Fragment() {

    private lateinit var binding: FragmentCharactersBinding
    private val viewModel: CharactersViewModel by viewModels()

    private val charactersAdapter: CharactersAdapter by lazy {
        CharactersAdapter(CharactersAdapter.OnClickListener { character ->
            val action =
                CharactersFragmentDirections.actionCharactersFragmentToCharactersDetailsFragment(
                    character
                )
            findNavController().navigate(action)
            binding.searchView.editText!!.setText("")
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharactersBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.searchView.setEndIconOnClickListener {
            setUpObserver(binding.searchView.editText!!.text.toString())
            binding.charactersProgressBar.isVisible = true
            hideKeyboard()
        }

        setUpObserver("")

        setUpAdapter()

        return view
    }

    private fun setUpObserver(searchString: String) {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.getCharacters(searchString).collect {
                charactersAdapter.submitData(lifecycle, it)
            }
        }
    }

    private fun setUpAdapter() {

        binding.charactersRecyclerview.apply {
            adapter = charactersAdapter
        }

        charactersAdapter.addLoadStateListener { loadState ->
            if (loadState.refresh is LoadState.Loading) {
                if (charactersAdapter.snapshot().isEmpty()) {
                    binding.charactersProgressBar.isVisible = true
                }
                binding.textViewError.isVisible = false

            } else {
                binding.charactersProgressBar.isVisible = false

                val error = when {
                    loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                    loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                    loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error

                    else -> null
                }
                error?.let {
                    if (charactersAdapter.snapshot().isEmpty()) {
                        binding.textViewError.isVisible = true
                        binding.textViewError.text = it.error.localizedMessage
                    }
                }
            }
        }
    }
}