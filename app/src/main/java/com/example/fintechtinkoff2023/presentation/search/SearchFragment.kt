package com.example.fintechtinkoff2023.presentation.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.TextView.OnEditorActionListener
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fintechtinkoff2023.R
import com.example.fintechtinkoff2023.data.network.model.search_films.SearchFilm
import com.example.fintechtinkoff2023.databinding.FragmentSearchBinding
import com.example.fintechtinkoff2023.domain.state.NetworkResult
import com.example.fintechtinkoff2023.presentation.film.FilmInfoFragment
import com.example.fintechtinkoff2023.presentation.search.adapter.SearchFilmsAdapter
import com.example.fintechtinkoff2023.presentation.utils.navigation
import com.example.fintechtinkoff2023.presentation.utils.text_handler.AfterTextChangedListener
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SearchFragment : Fragment() {

    lateinit var binding: FragmentSearchBinding
    private val viewModel by viewModels<SearchFilmsViewModel>()
    private val adapter = SearchFilmsAdapter()
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
    }

    private fun setupRecyclerView(searchFilms: List<SearchFilm>) {
        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvSearchFilm.adapter = adapter
        binding.rvSearchFilm.layoutManager = layoutManager
        adapter.submitList(searchFilms)
    }

    private fun setupListeners() {
        binding.edSearchFilmTextField.addTextChangedListener(object : AfterTextChangedListener{
            override fun afterTextChanged(p0: Editable?) {
                lifecycleScope.launch {
                    delay(1500)
                    viewModel.loadTopFilms(binding.edSearchFilmTextField.text.toString())
                }
            }
        })
        binding.imBackArrow.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
        adapter.onFilmItemClickListener = {
            val fragment = FilmInfoFragment.newInstanceEditItem(it.filmId)
            navigation(fragment)
        }
    }

    private fun observerTopFilmLiveDataFlow() {
        lifecycleScope.launch {
            viewModel.topFilms.observe(viewLifecycleOwner) {
                when (it) {
                    is NetworkResult.Error -> {
                        exceptionToggle(loadingException = true, loading = false, exception = it.message)
                    }
                    is NetworkResult.Success -> {
                        setupRecyclerView(checkNotNull(it.data).searchFilms)
                        exceptionToggle(loadingException = false, loading = false)
                    }
                    is NetworkResult.Loading -> {
                        exceptionToggle(loadingException = false, loading = true)
                    }
                }
            }
        }
    }

    private fun exceptionToggle(loadingException: Boolean, loading : Boolean, exception: String? = null) {
        loadingException.let {
            binding.rvSearchFilm.isVisible = !it
            binding.btNotFound.isVisible = it
        }
        binding.progressBar.isVisible = loading == true
    }


}