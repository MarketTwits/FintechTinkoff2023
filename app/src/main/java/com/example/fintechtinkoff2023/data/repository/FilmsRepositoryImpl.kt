package com.example.fintechtinkoff2023.data.repository

import androidx.lifecycle.LiveData
import com.example.fintechtinkoff2023.data.network.RetrofitInstance
import com.example.fintechtinkoff2023.data.network.model.RatingFilms
import com.example.fintechtinkoff2023.domain.FilmsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope

//class FilmsRepositoryImpl : FilmsRepository {
//
//    private val retroFitClient = RetrofitInstance.kinoPoiskApiInstance
//    val scope = MainScope()
//
//    override fun getTopFilms(): RatingFilms {
//        return retroFitClient.getTopFilms()
//    }
//
//    override fun getFilmInfo(fromSymbol: String): LiveData<RatingFilms> {
//        TODO("Not yet implemented")
//    }
//
//    override fun loadData() {
//        TODO("Not yet implemented")
//    }
//}