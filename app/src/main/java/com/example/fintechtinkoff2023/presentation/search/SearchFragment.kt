package com.example.fintechtinkoff2023.presentation.search

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.fintechtinkoff2023.core.ProvideViewModel
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
        viewModel = (requireActivity().application as ProvideViewModel)
            .viewModel(requireActivity(), SearchFilmsViewModel::class.java)
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
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
            //todo fix call listener after rotation
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