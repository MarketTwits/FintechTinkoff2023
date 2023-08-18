package com.example.fintechtinkoff2023.presentation.screens.favorites

import android.os.Bundle
import android.view.View
import com.example.fintechtinkoff2023.core.view.BaseFragment
import com.example.fintechtinkoff2023.databinding.FavoritesFilmsScreenBinding
import com.example.fintechtinkoff2023.databinding.FragmentFavoritesBinding
import com.example.fintechtinkoff2023.presentation.models.FilmUi
import com.example.fintechtinkoff2023.presentation.screens.favorites.adapter.FavoriteFilmsAdapter
import com.example.fintechtinkoff2023.presentation.screens.filmInfo.FilmInfoFragment
import com.example.fintechtinkoff2023.presentation.screens.popular.PopularFragment
import com.example.fintechtinkoff2023.presentation.screens.search.SearchFragment
import com.example.fintechtinkoff2023.presentation.utils.adapterListener.ItemActions
import com.example.fintechtinkoff2023.presentation.utils.navigationReplaceFragment


class FavoritesFragment : BaseFragment<FavoritesFilmViewModel,FragmentFavoritesBinding>(
    FragmentFavoritesBinding::inflate
) {
    override val clazz = FavoritesFilmViewModel::class.java
    private lateinit var favoriteScreenBinding : FavoritesFilmsScreenBinding
    private lateinit var adapter: FavoriteFilmsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = viewModel(requireActivity(), clazz)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setUpListeners()
        observeViewModel()
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        adapter = FavoriteFilmsAdapter(
            object : ItemActions.MutableWithoutRetry{
                override fun onClick(filmUi: FilmUi) {
                    val fragment = FilmInfoFragment.newInstanceEditItem(filmItemId = filmUi.filmId)
                    navigationReplaceFragment(fragment)
                }
                override fun onLongClick(filmUi: FilmUi) {
                    viewModel.addFilm(filmUi)
                }
            }
        )
        favoriteScreenBinding.rvSearchFilm.adapter = adapter
    }

    private fun observeViewModel() {
        viewModel.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    private fun setUpListeners() {
        favoriteScreenBinding = binding.favoriteScreen
        binding.btPopularity.setOnClickListener {
            navigationReplaceFragment(PopularFragment(), false)
        }
        favoriteScreenBinding.imSearch.setOnClickListener {
            navigationReplaceFragment(SearchFragment(), true)
        }
    }
}