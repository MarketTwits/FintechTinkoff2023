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
        val filmId = requireArguments().getInt(FILM_ITEM_ID)
        viewModel.loadInfoAboutFilm(filmId = filmId)
        viewModel.fetchFilm(filmId)
    }

    companion object {
        private const val FILM_ITEM_ID = "FILM_ITEM_ID"
        fun newInstanceEditItem(filmItemId: Int): FilmInfoFragment {
            return FilmInfoFragment().apply {
                arguments = Bundle().apply {
                    putInt(FILM_ITEM_ID, filmItemId)
                }
            }
        }
    }
}
