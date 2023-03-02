package com.example.fintechtinkoff2023.domain

import androidx.lifecycle.LiveData
import com.example.fintechtinkoff2023.data.network.model.PageFilm
import retrofit2.Response


interface FilmsRepository {

        suspend  fun getTopFilms()

}