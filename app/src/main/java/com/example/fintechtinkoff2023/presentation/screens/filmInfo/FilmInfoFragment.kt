package com.example.fintechtinkoff2023.presentation.screens.filmInfo


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.fintechtinkoff2023.core.sl.ProvideViewModel
import com.example.fintechtinkoff2023.databinding.FragmentFilmInfoBinding



class FilmInfoFragment : Fragment() {

    lateinit var binding: FragmentFilmInfoBinding
    private lateinit var viewModel : FilmInfoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentFilmInfoBinding.inflate(inflater, container, false)
        viewModel = (requireActivity().application as ProvideViewModel)
            .viewModel(this, FilmInfoViewModel::class.java)
        return binding.root
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