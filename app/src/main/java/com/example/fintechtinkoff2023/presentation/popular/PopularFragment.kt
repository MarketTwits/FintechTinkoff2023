package com.example.fintechtinkoff2023.presentation.popular

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fintechtinkoff2023.R
import com.example.fintechtinkoff2023.R.*
import com.example.fintechtinkoff2023.data.network.model.page_film.Film
import com.example.fintechtinkoff2023.databinding.FragmentPopularBinding
import com.example.fintechtinkoff2023.domain.state.NetworkResult
import com.example.fintechtinkoff2023.presentation.popular.adapter.TopFilmsAdapter
import com.example.fintechtinkoff2023.presentation.search.SearchFragment
import kotlinx.coroutines.launch


class PopularFragment : Fragment() {

    lateinit var binding: FragmentPopularBinding
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

    private fun setupRecyclerView(films: List<Film>) {
        val adapter = TopFilmsAdapter()
        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvTopFilms.adapter = adapter
        binding.rvTopFilms.layoutManager = layoutManager
        adapter.submitList(films)
    }

    private fun setupListeners() {
        binding.btRetry.setOnClickListener {
            viewModel.loadTopFilms()
        }
        binding.imSearch.setOnClickListener {
            navigation(SearchFragment())
        }
    }

    private fun observerTopFilmLiveDataFlow() {
        lifecycleScope.launch {
            viewModel.topFilms.observe(viewLifecycleOwner) {
                when (it) {
                    is NetworkResult.Error -> {
                        exceptionToggle(true, it.message)
                        binding.progressBar.isVisible = false
                    }
                    is NetworkResult.Success -> {
                        setupRecyclerView(it.data!!.films)
                        exceptionToggle(false)
                        binding.progressBar.isVisible = false
                    }
                    is NetworkResult.Loading -> {
                        exceptionToggle(false)
                        binding.progressBar.isVisible = true
                    }
                }
            }
        }
    }
    private fun exceptionToggle(loadingException: Boolean, exception: String? = null) {
        loadingException.let {
            binding.imExceptin.isVisible = it
            binding.tvExceptionMessage.isVisible = it
            binding.tvExceptionMessage.text = getString(string.check_your_connection, exception)
            binding.btRetry.isVisible = it
        }
    }
    private fun navigation(fragment: Fragment){
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView, fragment )
            .addToBackStack(null)
            .commit()
    }
}

