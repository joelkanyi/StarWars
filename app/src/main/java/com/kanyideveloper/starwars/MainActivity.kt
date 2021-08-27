package com.kanyideveloper.starwars

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.kanyideveloper.starwars.adapters.CharactersAdapter
import com.kanyideveloper.starwars.databinding.ActivityMainBinding
import com.kanyideveloper.starwars.utils.Resource
import com.kanyideveloper.starwars.viewmodels.CharactersViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: CharactersViewModel by viewModels()

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private val charactersAdapter: CharactersAdapter by lazy { CharactersAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.getCharacters().observe(this@MainActivity, Observer {
                when (it) {
                    is Resource.Success -> {
                        Timber.d("Success: ${it.data?.results.toString()}")
                        charactersAdapter.submitList(it.data?.results)
                        binding.recyclerview.adapter = charactersAdapter
                    }

                    is Resource.Failure -> {
                        Timber.d("Failure: ${it.message}")
                    }

                    is Resource.Loading -> {
                        Timber.d("Loading...")
                    }
                }
            })
        }
    }
}