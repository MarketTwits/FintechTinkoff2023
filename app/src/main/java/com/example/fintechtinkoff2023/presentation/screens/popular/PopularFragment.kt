package com.example.fintechtinkoff2023.presentation.screens.popular

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.fintechtinkoff2023.core.sl.ProvideViewModel
import com.example.fintechtinkoff2023.databinding.FragmentPopularBinding
import com.example.fintechtinkoff2023.databinding.PopularFilmsScreenBinding
import com.example.fintechtinkoff2023.presentation.models.FilmUi
import com.example.fintechtinkoff2023.presentation.screens.favorites.FavoritesFragment
import com.example.fintechtinkoff2023.presentation.screens.filmInfo.FilmInfoFragment
import com.example.fintechtinkoff2023.presentation.screens.popular.adapter.PopularFilmsAdapter
import com.example.fintechtinkoff2023.presentation.screens.search.SearchFragment
import com.example.fintechtinkoff2023.presentation.utils.adapterListener.ItemClick
import com.example.fintechtinkoff2023.presentation.utils.adapterListener.ItemLongClick
import com.example.fintechtinkoff2023.presentation.utils.adapterListener.Retry
import com.example.fintechtinkoff2023.presentation.utils.navigationReplaceFragment


class PopularFragment : Fragment() {

    lateinit var binding: FragmentPopularBinding
    lateinit var popularScreenBinding : PopularFilmsScreenBinding
    private lateinit var adapter: PopularFilmsAdapter
    lateinit var viewModel: PopularFilmsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (requireActivity().application as ProvideViewModel)
            .viewModel(requireActivity(), PopularFilmsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentPopularBinding.inflate(inflater, container, false)
        popularScreenBinding = binding.popularScreen
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observerTopFilmLiveDataFlow()
        setupListeners()
        setupRecyclerView()
    }
    private fun setupRecyclerView() {
        adapter = PopularFilmsAdapter(
            object : Retry {
                override fun retry() {
                  viewModel.fetchTopFilms()
                }
            }, object : ItemClick {
                override fun onClick(filmUi: FilmUi) {
                    val fragment = FilmInfoFragment.newInstanceEditItem(filmItemId = filmUi.filmId)
                    navigationReplaceFragment(fragment)
                }
            }, object : ItemLongClick {
                override fun onLongClick(filmUi: FilmUi) {
                    viewModel.itemToCache(filmUi)
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
        binding.btFavorites.setOnClickListener {
            navigationReplaceFragment(FavoritesFragment(), false)
        }
    }

    private fun observerTopFilmLiveDataFlow() {
        viewModel.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }
}

