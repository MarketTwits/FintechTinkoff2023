package com.example.fintechtinkoff2023.presentation.popular

import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fintechtinkoff2023.data.network.retrofit.RetrofitInstance
import com.example.fintechtinkoff2023.data.network.model.Film
import com.example.fintechtinkoff2023.data.network.model.PageFilm
import com.example.fintechtinkoff2023.databinding.FragmentPopularBinding
import com.example.fintechtinkoff2023.domain.state.NetworkResult
import com.example.fintechtinkoff2023.domain.state.State
import com.example.fintechtinkoff2023.presentation.popular.adapter.TopFilmsAdapter
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collect
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
    }

    private fun setupRecyclerView(films: List<Film>) {
        val adapter = TopFilmsAdapter()
        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvTopFilms.adapter = adapter
        binding.rvTopFilms.layoutManager = layoutManager
        lifecycleScope.launch {
            adapter.submitList(films)
        }
    }
    private fun observerTopFilmLiveDataFlow() {
        lifecycleScope.launch {
            viewModel.topFilms.observe(viewLifecycleOwner){
               when(it){
                   is NetworkResult.Error -> {
                       Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                       binding.progressBar.isVisible = false
                   }
                   is NetworkResult.Success -> {
                       setupRecyclerView(it.data!!.films)
                       binding.progressBar.isVisible = false
                   }
                   is NetworkResult.Loading -> {
                        binding.progressBar.isVisible = true
                   }
               }
            }
        }

    }
}

