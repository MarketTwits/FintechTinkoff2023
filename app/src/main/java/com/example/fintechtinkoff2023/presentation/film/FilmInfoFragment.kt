package com.example.fintechtinkoff2023.presentation.film


import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.bumptech.glide.request.target.Target
import com.example.fintechtinkoff2023.R
import com.example.fintechtinkoff2023.data.network.model.item_film.InfoFilm
import com.example.fintechtinkoff2023.databinding.FragmentFilmInfoBinding
import com.example.fintechtinkoff2023.domain.state.NetworkResult
import kotlinx.coroutines.launch


class FilmInfoFragment : Fragment() {
    lateinit var binding: FragmentFilmInfoBinding
    private val viewModel by viewModels<FilmInfoViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentFilmInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadInfoAboutFilm()
        observerTopFilmLiveDataFlow()
        setupListeners()
    }
    private fun loadInfoAboutFilm(){
        val filmId = requireArguments().getInt(FILM_ITEM_ID)
        lifecycleScope.launch {
            viewModel.loadTopFilms(filmId = filmId)
        }
    }
    private fun setupListeners(){
        binding.btRetryInfoFilm.setOnClickListener {
            loadInfoAboutFilm()
        }
    }
    private fun observerTopFilmLiveDataFlow() {
        lifecycleScope.launch {
            viewModel.infoFilms.observe(viewLifecycleOwner) {
                when (it) {
                    is NetworkResult.Error -> {
                        exceptionToggle(loadingException = true, loading = false, exception = it.message)
                    }
                    is NetworkResult.Success -> {
                        setUpUI(checkNotNull(it.data))
                        exceptionToggle(loadingException = false, loading = false)
                    }
                    is NetworkResult.Loading -> {
                        exceptionToggle(loadingException = false, loading = true)
                    }
                }
            }
        }
    }
    @SuppressLint("StringFormatInvalid", "SetTextI18n")
    private fun setUpUI(film : InfoFilm){
        binding.tvFilmName.text = film.nameRu
        binding.tvCountry.text = getString(R.string.countries) + film.countries.map { it.country }.joinToString()
        binding.tvGenres.text = getString(R.string.genres) + film.genres.map { it.genre }.joinToString()
        binding.tvFilmDescription.text = film.description
        Glide
            .with(requireContext())
            .load(film.posterUrl)
            .into(binding.imFilmPoster)
    }

    private fun exceptionToggle(loadingException: Boolean, loading : Boolean, exception: String? = null) {
        loadingException.let {
            binding.btRetryInfoFilm.isVisible = it
            binding.imExceptinInfoFilm.isVisible = it
            binding.tvExceptionMessageInfoFilm.isVisible = it
            binding.tvExceptionMessageInfoFilm.text = getString(R.string.check_your_connection, exception)
        }
         binding.progressBarInfoFilm.isVisible = loading == true

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