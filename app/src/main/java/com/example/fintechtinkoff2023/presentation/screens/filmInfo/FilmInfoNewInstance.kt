package com.example.fintechtinkoff2023.presentation.screens.filmInfo

import android.os.Bundle

interface FilmInfoNewInstance {
    fun newInstanceInfoFragment(filmItemId: Int): FilmInfoFragment
    fun fetchFilmId(bundle: Bundle) : Int
    class Base : FilmInfoNewInstance {
        override fun newInstanceInfoFragment(filmItemId: Int) =
            FilmInfoFragment().apply {
                arguments = Bundle().apply {
                    putInt(FILM_ITEM_ID, filmItemId)
                }
            }
        override fun fetchFilmId(bundle: Bundle) = bundle.getInt(FILM_ITEM_ID)
    }
    companion object{
        private const val FILM_ITEM_ID = "FILM_ITEM_ID"
    }
}
