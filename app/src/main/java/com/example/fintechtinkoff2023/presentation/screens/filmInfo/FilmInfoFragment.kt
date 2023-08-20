package com.example.fintechtinkoff2023.presentation.screens.filmInfo


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.fintechtinkoff2023.R
import com.example.fintechtinkoff2023.core.sl.ProvideViewModel
import com.example.fintechtinkoff2023.core.view.BaseFragment
import com.example.fintechtinkoff2023.databinding.FragmentFavoritesBinding
import com.example.fintechtinkoff2023.databinding.FragmentFilmInfoBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class FilmInfoFragment : BaseFragment<FilmInfoViewModel, FragmentFilmInfoBinding>(
    FragmentFilmInfoBinding::inflate
) {
    override val clazz = FilmInfoViewModel::class.java
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = viewModel(this, clazz)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
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