package com.example.fintechtinkoff2023.presentation.screens.popular

import android.os.Bundle
import android.view.View
import com.example.fintechtinkoff2023.core.view.BaseFragment
import com.example.fintechtinkoff2023.databinding.FragmentPopularBinding
import com.example.fintechtinkoff2023.databinding.PopularFilmsScreenBinding
import com.example.fintechtinkoff2023.presentation.models.FilmUi
import com.example.fintechtinkoff2023.presentation.screens.filmInfo.FilmInfoFragment
import com.example.fintechtinkoff2023.presentation.screens.popular.adapter.PopularFilmsAdapter
import com.example.fintechtinkoff2023.presentation.screens.search.SearchFragment
import com.example.fintechtinkoff2023.presentation.utils.adapterListener.ItemActions
import com.example.fintechtinkoff2023.presentation.utils.navigationReplaceFragment

class PopularFragment : BaseFragment<PopularFilmsViewModel, FragmentPopularBinding>(
    FragmentPopularBinding::inflate
) {

    private lateinit var popularScreenBinding : PopularFilmsScreenBinding
    private lateinit var adapter: PopularFilmsAdapter
    override val clazz = PopularFilmsViewModel::class.java
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = viewModel(requireActivity(), clazz)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        popularScreenBinding = binding.popularScreen
        observerTopFilmLiveDataFlow()
        setupListeners()
        setupRecyclerView()
    }
    private fun setupRecyclerView() {
        adapter = PopularFilmsAdapter(
            object : ItemActions.Mutable{
                override fun onClick(filmUi: FilmUi) {
                    val fragment = FilmInfoFragment.newInstanceEditItem(filmUi.filmId)
                    navigationReplaceFragment(fragment)
                }
                override fun onLongClick(filmUi: FilmUi) {
                    viewModel.itemToCache(filmUi)
                }
                override fun retry() {
                    viewModel.fetchTopFilms()
                }
            }
        )
        popularScreenBinding.rvTopFilms.adapter = adapter
        popularScreenBinding.rvTopFilms.clearAnimation()
        popularScreenBinding.rvTopFilms.itemAnimator?.changeDuration = 0
    }
    private fun setupListeners() {
        popularScreenBinding.imSearch.setOnClickListener {
            navigationReplaceFragment(SearchFragment())
        }
    }
    private fun observerTopFilmLiveDataFlow() {
        viewModel.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }
}

