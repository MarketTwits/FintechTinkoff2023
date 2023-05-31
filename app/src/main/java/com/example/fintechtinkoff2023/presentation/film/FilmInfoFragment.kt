package com.example.fintechtinkoff2023.presentation.film


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.fintechtinkoff2023.FintechApp
import com.example.fintechtinkoff2023.R
import com.example.fintechtinkoff2023.core.ProvideViewModel
import com.example.fintechtinkoff2023.data.network.model.item_film.InfoFilmCloud
import com.example.fintechtinkoff2023.databinding.FragmentFilmInfoBinding
import com.example.fintechtinkoff2023.domain.model.FilmInfoUi
import com.example.fintechtinkoff2023.domain.state.NetworkResult
import com.example.fintechtinkoff2023.presentation.search.SearchFilmsViewModel
import com.example.fintechtinkoff2023.presentation.utils.formatBoldString
import kotlinx.coroutines.launch


class FilmInfoFragment : Fragment() {

    lateinit var binding: FragmentFilmInfoBinding
    private lateinit var viewModel : FilmInfoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        viewModel = (requireActivity().application as ProvideViewModel)
            .viewModel(this, FilmInfoViewModel::class.java)
        binding = FragmentFilmInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadInfoAboutFilm()
        observerTopFilmLiveDataFlow()
    }
    private fun loadInfoAboutFilm(){
        val filmId = requireArguments().getInt(FILM_ITEM_ID)
        lifecycleScope.launch {
            viewModel.loadInfoAboutFilm(filmId = filmId)
        }
    }

    private fun observerTopFilmLiveDataFlow() {
        lifecycleScope.launch {
            viewModel.infoFilmsCloud.observe(viewLifecycleOwner) {
                when(it){
                    is FilmInfoUi.Base ->   setUpUI(it)
                    is FilmInfoUi.Progress -> {}//todo
                    is FilmInfoUi.Failed -> {Toast.makeText(requireContext(), "Failed", Toast.LENGTH_LONG).show()} //todo
                    else -> {} //todo
                }
            }
        }
    }
    private fun setUpUI(film : FilmInfoUi){
        binding.tvFilmName.text = film.name
        binding.tvCountry.text = formatBoldString( getString(R.string.countries), film.country.joinToString { it.country })
        binding.tvGenres.text = formatBoldString( getString(R.string.genres), film.genres.joinToString { it.genre })
        binding.tvFilmDescription.text = film.description
        Glide
            .with(requireContext())
            .load(film.posterUrl)
            .into(binding.imFilmPoster)
    }
    companion object {
        const val FILM_ITEM_ID = "FILM_ITEM_ID"
        fun newInstanceEditItem(filmItemId: Int): FilmInfoFragment {
            return FilmInfoFragment().apply {
                arguments = Bundle().apply {
                    putInt(FILM_ITEM_ID, filmItemId)
                }
            }
        }
    }


}