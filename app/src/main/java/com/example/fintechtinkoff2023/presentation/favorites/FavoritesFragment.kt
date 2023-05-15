package com.example.fintechtinkoff2023.presentation.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fintechtinkoff2023.FintechApp
import com.example.fintechtinkoff2023.databinding.FragmentFavoritesBinding
import com.example.fintechtinkoff2023.domain.model.FilmUi
import com.example.fintechtinkoff2023.presentation.favorites.adapter.FavoriteFilmsAdapter
import com.example.fintechtinkoff2023.presentation.film.FilmInfoFragment
import com.example.fintechtinkoff2023.presentation.popular.PopularFragment
import com.example.fintechtinkoff2023.presentation.search.SearchFragment
import com.example.fintechtinkoff2023.presentation.utils.adapterListener.ItemClick
import com.example.fintechtinkoff2023.presentation.utils.adapterListener.ItemLongClick
import com.example.fintechtinkoff2023.presentation.utils.navigation
import kotlinx.coroutines.launch


class FavoritesFragment : Fragment() {
    lateinit var viewModel: FavoritesFilmViewModel
    lateinit var binding: FragmentFavoritesBinding
    private lateinit var adapter: FavoriteFilmsAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        viewModel = (requireActivity().application as FintechApp).favoritesFilmViewModel
        binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        adapter = FavoriteFilmsAdapter(
            object : ItemClick {
            override fun onClick(filmUi: FilmUi) {
                val fragment = FilmInfoFragment.newInstanceEditItem(filmItemId = filmUi.filmId)
                navigation(fragment)
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
        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvSearchFilm.adapter = adapter
        binding.rvSearchFilm.layoutManager = layoutManager
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.liveData.observe(viewLifecycleOwner) {
                adapter.submitList(it)
            }
        }
    }

    private fun setUpListeners() {
        binding.btPopularity.setOnClickListener {
            navigation(PopularFragment(), false)
        }
        binding.imSearch.setOnClickListener {
            navigation(SearchFragment(), true)
        }
    }
}