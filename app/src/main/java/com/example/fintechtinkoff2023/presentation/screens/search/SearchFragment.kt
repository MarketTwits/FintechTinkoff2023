package com.example.fintechtinkoff2023.presentation.screens.search

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.example.fintechtinkoff2023.core.view.BaseFragment
import com.example.fintechtinkoff2023.databinding.FragmentSearchBinding
import com.example.fintechtinkoff2023.presentation.models.FilmUi
import com.example.fintechtinkoff2023.presentation.screens.filmInfo.FilmInfoFragment
import com.example.fintechtinkoff2023.presentation.screens.search.adapter.SearchFilmsAdapter
import com.example.fintechtinkoff2023.presentation.utils.adapterListener.ItemActions
import com.example.fintechtinkoff2023.presentation.utils.afterTextChangedDelayed
import com.example.fintechtinkoff2023.presentation.utils.navigationReplaceFragment
import kotlinx.coroutines.launch


class SearchFragment : BaseFragment<SearchFilmsViewModel, FragmentSearchBinding>(
    FragmentSearchBinding::inflate
) {
    override val clazz =  SearchFilmsViewModel::class.java

    private val adapter = SearchFilmsAdapter(
        object : ItemActions.Mutable{
            override fun onClick(filmUi: FilmUi) {
                val fragment = FilmInfoFragment.newInstance.newInstanceInfoFragment(filmUi.filmId)
                navigationReplaceFragment(fragment)
            }
            override fun onLongClick(filmUi: FilmUi) {
                viewModel.itemToCache(filmUi)
            }
            override fun retry() {
                viewModel.listenEditText(binding.edSearchFilmTextField.text.toString())
            }
        }
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = viewModel(requireActivity(), clazz)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observerTopFilmLiveDataFlow()
        setupRecyclerView()
        setupListeners()
    }

    private fun setupRecyclerView() {
        binding.rvSearchFilm.adapter = adapter
        binding.rvSearchFilm.clearAnimation()
        binding.rvSearchFilm.itemAnimator?.changeDuration = 0
    }

    private fun setupListeners() {
        binding.edSearchFilmTextField.afterTextChangedDelayed {
            viewModel.listenEditText(binding.edSearchFilmTextField.text.toString())
        }
        binding.imBackArrow.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    private fun observerTopFilmLiveDataFlow() {
        lifecycleScope.launch {
            viewModel.observe(viewLifecycleOwner) {
                adapter.submitList(it)
            }
        }
    }
}