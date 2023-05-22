package com.example.fintechtinkoff2023.presentation.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fintechtinkoff2023.FintechApp
import com.example.fintechtinkoff2023.databinding.FragmentSearchBinding
import com.example.fintechtinkoff2023.domain.model.FilmUi
import com.example.fintechtinkoff2023.presentation.film.FilmInfoFragment
import com.example.fintechtinkoff2023.presentation.search.adapter.SearchFilmsAdapter
import com.example.fintechtinkoff2023.presentation.utils.adapterListener.ItemClick
import com.example.fintechtinkoff2023.presentation.utils.adapterListener.ItemLongClick
import com.example.fintechtinkoff2023.presentation.utils.adapterListener.Retry
import com.example.fintechtinkoff2023.presentation.utils.afterTextChangedDelayed
import com.example.fintechtinkoff2023.presentation.utils.navigation
import kotlinx.coroutines.launch


class SearchFragment : Fragment() {

    lateinit var binding: FragmentSearchBinding
    lateinit var viewModel: SearchFilmsViewModel
    private val adapter = SearchFilmsAdapter(
        object : Retry {
            override fun retry() {
                lifecycleScope.launch {//todo
                    viewModel.loadTopFilms(binding.edSearchFilmTextField.text.toString())
                }
            }
        }, object : ItemClick {
            override fun onClick(filmUi: FilmUi) {
                val fragment = FilmInfoFragment.newInstanceEditItem(filmItemId = filmUi.filmId)
                navigation(fragment)
            }
        }, object : ItemLongClick {
            override fun onLongClick(filmUi: FilmUi) {
                viewModel.itemToCache(filmUi)
            }
        }
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        viewModel = (requireActivity().application as FintechApp).searchFilmsViewModel
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observerTopFilmLiveDataFlow()
        setupListeners()
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvSearchFilm.adapter = adapter
        binding.rvSearchFilm.layoutManager = layoutManager
    }

    private fun setupListeners() {
        binding.edSearchFilmTextField.afterTextChangedDelayed {
            viewModel.loadTopFilms(binding.edSearchFilmTextField.text.toString())
        }
        binding.imBackArrow.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    private fun observerTopFilmLiveDataFlow() {
        lifecycleScope.launch {
            viewModel.topFilms.observe(viewLifecycleOwner) {
                adapter.submitList(it)
            }
        }
    }
}