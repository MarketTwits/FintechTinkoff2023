package com.example.fintechtinkoff2023.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.fintechtinkoff2023.data.network.retrofit.RetrofitInstance
import com.example.fintechtinkoff2023.data.network.model.PageFilm
import com.example.fintechtinkoff2023.domain.state.NetworkResult
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import retrofit2.HttpException
import retrofit2.Response

class FilmsRepositoryImpl {

    private val movieApi = RetrofitInstance.kinoPoiskApiInstance

    val flow = MutableSharedFlow<NetworkResult<PageFilm>>()

    private val excetionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.d("MainViewModel", "Exception caught: $throwable")
    }
    private val scope = CoroutineScope(Dispatchers.IO + excetionHandler)

     fun getTopMovie() {
         scope.launch {
             try {
                 val pageFilm = movieApi.getTopFilms()
                 flow.emit(NetworkResult.Success(pageFilm))
             }catch (e : Exception){
                 flow.emit(NetworkResult.Error(e.message))
             }
         }
     }
}

