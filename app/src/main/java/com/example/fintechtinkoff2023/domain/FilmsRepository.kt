package com.example.fintechtinkoff2023.domain

import androidx.lifecycle.LiveData
import com.example.fintechtinkoff2023.data.network.model.RatingFilms

interface FilmsRepository {

        fun getTopFilms(): RatingFilms

        fun getFilmInfo(fromSymbol: String): LiveData<RatingFilms>

        fun loadData()
}