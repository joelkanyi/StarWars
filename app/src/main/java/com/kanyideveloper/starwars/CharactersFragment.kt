package com.kanyideveloper.starwars

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.kanyideveloper.starwars.adapters.CharactersAdapter
import com.kanyideveloper.starwars.databinding.FragmentCharactersBinding
import com.kanyideveloper.starwars.viewmodels.CharactersViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class CharactersFragment : Fragment() {

    private lateinit var binding: FragmentCharactersBinding
    private val viewModel: CharactersViewModel by viewModels()
    private val charactersAdapter: CharactersAdapter by lazy {
        CharactersAdapter(CharactersAdapter.OnClickListener { character ->
            Toast.makeText(requireContext(), "${character.name}", Toast.LENGTH_SHORT).show()
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCharactersBinding.inflate(inflater, container, false)
        val view = binding.root

        initObservers()
        setUpAdapter()

        return view
    }

    private fun initObservers() {
        lifecycleScope.launch {
            viewModel.getCharacters(binding.searchView.text.toString()).observe(viewLifecycleOwner, {
                charactersAdapter.submitData(lifecycle, it)
            })
        }
    }

    private fun setUpAdapter() {

        binding.charactersRecyclerview.apply {
            adapter = charactersAdapter
        }

        charactersAdapter.addLoadStateListener { loadState ->
            if (loadState.refresh is LoadState.Loading) {
                if (charactersAdapter.snapshot().isEmpty()) {
                    //binding.progressIndicator.isVisible = true
                    Timber.d("Loading...")
                }
                //binding.errorTextView.isVisible = false

            } else {
                //binding.progressIndicator.isVisible = false
                Timber.d("Stopped Loading...")

                val error = when {
                    loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                    loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                    loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error

                    else -> null
                }
                error?.let {
                    if (charactersAdapter.snapshot().isEmpty()) {
                        Timber.d("Error while loading: ${it.error.localizedMessage}")
                        //binding.errorTextView.isVisible = true
                        //binding.errorTextView.text = it.error.localizedMessage
                    }
                }
            }
        }
    }
}