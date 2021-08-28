package com.kanyideveloper.starwars

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.kanyideveloper.starwars.adapters.CharactersAdapter
import com.kanyideveloper.starwars.databinding.ActivityMainBinding
import com.kanyideveloper.starwars.utils.Resource
import com.kanyideveloper.starwars.viewmodels.CharactersViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: CharactersViewModel by viewModels()

    private lateinit var binding: ActivityMainBinding
    private val charactersAdapter: CharactersAdapter by lazy { CharactersAdapter() }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpAdapter()

        binding.buttonSearch.setOnClickListener {
            startReqJob()
        }
    }

    private fun startReqJob(){
        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.getCharacters(binding.editTextSearch.text.toString()).collectLatest {
                charactersAdapter.submitData(it)
            }
        }
    }


    private fun setUpAdapter() {

        binding.recyclerview.apply {
            adapter = charactersAdapter
        }

        startReqJob()

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