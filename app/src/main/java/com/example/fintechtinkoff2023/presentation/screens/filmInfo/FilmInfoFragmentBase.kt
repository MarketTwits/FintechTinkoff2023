package com.example.fintechtinkoff2023.presentation.screens.filmInfo


import android.os.Bundle
import android.view.View
import com.example.fintechtinkoff2023.core.view.BaseFragment
import com.example.fintechtinkoff2023.databinding.FragmentFilmInfoBinding


class FilmInfoFragment : BaseFragment<FilmInfoViewModel, FragmentFilmInfoBinding>(
    FragmentFilmInfoBinding::inflate
) {
    override val clazz = FilmInfoViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = viewModel(this, clazz)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val filmId = FilmInfoNewInstance.Base().fetchFilmId(requireArguments())
        viewModel.loadInfoAboutFilm(filmId = filmId)
        viewModel.fetchFilm(filmId)
        viewModel.observeRetry(viewLifecycleOwner){
            viewModel.loadInfoAboutFilm(filmId)
        }
    }
    companion object {
        val newInstance = FilmInfoNewInstance.Base()
    }
}
