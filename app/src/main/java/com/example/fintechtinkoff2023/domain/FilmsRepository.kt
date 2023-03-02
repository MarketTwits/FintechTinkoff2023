package com.example.fintechtinkoff2023.domain


interface FilmsRepository {

        suspend  fun getTopFilms()

}