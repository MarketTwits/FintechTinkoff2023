package com.example.fintechtinkoff2023.presentation.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fintechtinkoff2023.R
import com.example.fintechtinkoff2023.data.network.model.search_films.Film
import com.example.fintechtinkoff2023.data.network.retrofit.RetrofitInstance
import com.example.fintechtinkoff2023.databinding.FragmentSearchBinding
import com.example.fintechtinkoff2023.domain.state.NetworkResult
import com.example.fintechtinkoff2023.presentation.search.adapter.SearchFilmsAdapter
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SearchFragment : Fragment() {

    lateinit var binding: FragmentSearchBinding
    private val viewModel by viewModels<SearchFilmsViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observerTopFilmLiveDataFlow()
        setupListeners()
        //request()


    }

    private fun setupRecyclerView(films: List<Film>) {
        val adapter = SearchFilmsAdapter()
        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvSearchFilm.adapter = adapter
        binding.rvSearchFilm.layoutManager = layoutManager
        adapter.submitList(films)
    }

    private fun setupListeners() {
        binding.edSearchFilm.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun afterTextChanged(p0: Editable?) {
                lifecycleScope.launch {
                    delay(1500)
                    viewModel.loadTopFilms(binding.edSearchFilm.text.toString())
                }
            }
        })
    }

    private fun observerTopFilmLiveDataFlow() {
        lifecycleScope.launch {
            viewModel.topFilms.observe(viewLifecycleOwner) {
                when (it) {
                    is NetworkResult.Error -> {
                        //exceptionToggle(true, it.message)
                        binding.progressBar.isVisible = false
                        binding.btNotFound.isVisible = true
                    }
                    is NetworkResult.Success -> {
                        setupRecyclerView(it.data!!.films)
                        //exceptionToggle(false)
                        binding.progressBar.isVisible = false
                        binding.btNotFound.isVisible = false
                    }
                    is NetworkResult.Loading -> {
                        //exceptionToggle(false)
                        binding.progressBar.isVisible = true
                        binding.btNotFound.isVisible = false
                    }
                }
            }
        }
    }

    private fun exceptionToggle(loadingException: Boolean, exception: String? = null) {
        loadingException.let {
            binding.rvSearchFilm.isVisible = it
            binding.btNotFound.isVisible = it
        }
    }

    private fun navigation(fragment: Fragment) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView, fragment)
            .addToBackStack(null)
            .commit()
    }

    fun request() {
        lifecycleScope.launch {
            val response = RetrofitInstance.kinoPoiskApiInstance.getFilmsByKeyWords("Звезные войны")
            //Log.e("Test", response.searchFilms.size.toString())
        }
    }
}