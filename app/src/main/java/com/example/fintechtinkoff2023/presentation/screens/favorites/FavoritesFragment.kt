package com.example.fintechtinkoff2023.presentation.screens.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.fintechtinkoff2023.core.sl.ProvideViewModel
import com.example.fintechtinkoff2023.databinding.FavoritesFilmsScreenBinding
import com.example.fintechtinkoff2023.databinding.FragmentFavoritesBinding
import com.example.fintechtinkoff2023.presentation.models.FilmUi
import com.example.fintechtinkoff2023.presentation.screens.favorites.adapter.FavoriteFilmsAdapter
import com.example.fintechtinkoff2023.presentation.screens.filmInfo.FilmInfoFragment
import com.example.fintechtinkoff2023.presentation.screens.popular.PopularFragment
import com.example.fintechtinkoff2023.presentation.screens.search.SearchFragment
import com.example.fintechtinkoff2023.presentation.utils.adapterListener.ItemClick
import com.example.fintechtinkoff2023.presentation.utils.adapterListener.ItemLongClick
import com.example.fintechtinkoff2023.presentation.utils.navigationReplaceFragment


class FavoritesFragment : Fragment() {
    lateinit var viewModel: FavoritesFilmViewModel
    lateinit var binding: FragmentFavoritesBinding
    lateinit var favoriteScreenBinding : FavoritesFilmsScreenBinding
    private lateinit var adapter: FavoriteFilmsAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        viewModel = (requireActivity().application as ProvideViewModel)
            .viewModel(requireActivity(), FavoritesFilmViewModel::class.java)
        binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        favoriteScreenBinding = binding.favoriteScreen
        adapter = FavoriteFilmsAdapter(
            object : ItemClick {
                override fun onClick(filmUi: FilmUi) {
                    val fragment = FilmInfoFragment.newInstanceEditItem(filmItemId = filmUi.filmId)
                    navigationReplaceFragment(fragment)
                }
            },
            object : ItemLongClick {
                override fun onLongClick(filmUi: FilmUi) {
                    viewModel.addFilm(filmUi)
                }
            }
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpListeners()
        observeViewModel()
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        favoriteScreenBinding.rvSearchFilm.adapter = adapter
    }

    private fun observeViewModel() {
        viewModel.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    private fun setUpListeners() {
        binding.btPopularity.setOnClickListener {
            navigationReplaceFragment(PopularFragment(), false)
        }
        favoriteScreenBinding.imSearch.setOnClickListener {
            navigationReplaceFragment(SearchFragment(), true)
        }
    }
}