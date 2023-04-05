package com.example.fintechtinkoff2023.presentation.popular

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fintechtinkoff2023.R.string
import com.example.fintechtinkoff2023.data.network.model.page_film.TopFilm
import com.example.fintechtinkoff2023.databinding.FragmentPopularBinding
import com.example.fintechtinkoff2023.domain.state.NetworkResult
import com.example.fintechtinkoff2023.presentation.favorites.FavoritesFragment
import com.example.fintechtinkoff2023.presentation.film.FilmInfoFragment
import com.example.fintechtinkoff2023.presentation.popular.adapter.TopFilmsAdapter
import com.example.fintechtinkoff2023.presentation.search.SearchFragment
import com.example.fintechtinkoff2023.presentation.utils.navigation
import kotlinx.coroutines.launch


class PopularFragment : Fragment() {

    lateinit var binding: FragmentPopularBinding
    private val adapter = TopFilmsAdapter()
    private val viewModel by viewModels<PopularFilmsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentPopularBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observerTopFilmLiveDataFlow()
        setupListeners()
    }

    private fun setupRecyclerView(topFilms: List<TopFilm>) {
        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvTopFilms.adapter = adapter
        binding.rvTopFilms.layoutManager = layoutManager
        adapter.submitList(topFilms)
    }

    private fun setupListeners() {
        binding.btRetry.setOnClickListener {
            viewModel.loadTopFilms()
        }
        binding.imSearch.setOnClickListener {
            navigation(SearchFragment())
        }
        binding.btFavorites.setOnClickListener {
            navigation(FavoritesFragment(), false)
        }
        adapter.onFilmItemClickListener = {
            val fragment = FilmInfoFragment.newInstanceEditItem(filmItemId = it.filmId)
            navigation(fragment)
        }
    }

    private fun observerTopFilmLiveDataFlow() {
        lifecycleScope.launch {
            viewModel.topFilms.observe(viewLifecycleOwner) {
                when (it) {
                    is NetworkResult.Error -> {
                        exceptionToggle(true, exception = it.message, loading = false)
                    }
                    is NetworkResult.Success -> {
                        setupRecyclerView(checkNotNull(it.data).topFilms)
                        exceptionToggle(false, loading = false)
                    }
                    is NetworkResult.Loading -> {
                        exceptionToggle(false, loading = true)
                    }
                }
            }
        }
    }

    private fun exceptionToggle(
        loadingException: Boolean,
        loading: Boolean,
        exception: String? = null,
    ) {
        loadingException.let {
            binding.imExceptin.isVisible = it
            binding.tvExceptionMessage.isVisible = it
            binding.tvExceptionMessage.text = getString(string.check_your_connection, exception)
            binding.btRetry.isVisible = it
        }
        binding.progressBar.isVisible = loading == true

    }

}

