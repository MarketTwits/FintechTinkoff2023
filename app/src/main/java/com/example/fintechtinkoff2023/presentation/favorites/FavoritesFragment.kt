package com.example.fintechtinkoff2023.presentation.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.fintechtinkoff2023.core.sl.ProvideViewModel
import com.example.fintechtinkoff2023.databinding.FragmentFavoritesBinding
import com.example.fintechtinkoff2023.domain.model.FilmUi
import com.example.fintechtinkoff2023.presentation.favorites.adapter.FavoriteFilmsAdapter
import com.example.fintechtinkoff2023.presentation.filmInfo.FilmInfoFragment
import com.example.fintechtinkoff2023.presentation.popular.PopularFragment
import com.example.fintechtinkoff2023.presentation.search.SearchFragment
import com.example.fintechtinkoff2023.presentation.utils.adapterListener.ItemClick
import com.example.fintechtinkoff2023.presentation.utils.adapterListener.ItemLongClick
import com.example.fintechtinkoff2023.presentation.utils.navigationReplaceFragment


class FavoritesFragment : Fragment() {
    lateinit var viewModel: FavoritesFilmViewModel
    lateinit var binding: FragmentFavoritesBinding
    private lateinit var adapter: FavoriteFilmsAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        viewModel = (requireActivity().application as ProvideViewModel)
            .viewModel(requireActivity(), FavoritesFilmViewModel::class.java)
        binding = FragmentFavoritesBinding.inflate(inflater, container, false)
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
        binding.rvSearchFilm.adapter = adapter
        binding.rvSearchFilm.clearAnimation()
        binding.rvSearchFilm.itemAnimator?.changeDuration = 0
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
        binding.imSearch.setOnClickListener {
            navigationReplaceFragment(SearchFragment(), true)
        }
    }
}