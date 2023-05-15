package com.example.fintechtinkoff2023.domain


import android.util.Log
import com.example.fintechtinkoff2023.data.database.db_entites.FilmCache
import com.example.fintechtinkoff2023.data.network.model.item_film.InfoFilmCloud
import com.example.fintechtinkoff2023.data.network.retrofit.RetrofitInstance
import com.example.fintechtinkoff2023.domain.base_source.CacheDataSource
import com.example.fintechtinkoff2023.domain.base_source.ItemsSearchComparison
import com.example.fintechtinkoff2023.domain.base_source.ItemsTopComparison
import com.example.fintechtinkoff2023.domain.model.FilmBase
import com.example.fintechtinkoff2023.domain.model.FilmUi
import com.example.fintechtinkoff2023.domain.state.NetworkResult
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class FilmsRepositoryImpl(
    private val cacheDataSource: CacheDataSource,
    private val itemsTopComparison: ItemsTopComparison,
    private val itemsSearchComparison: ItemsSearchComparison
) {

    private val movieApi = RetrofitInstance.kinoPoiskApiInstance

    val topFilms = MutableSharedFlow<NetworkResult<List<FilmUi>>>()

    val searchFilms = MutableSharedFlow<NetworkResult<List<FilmUi>>>()

    val infoFilmCloud = MutableSharedFlow<NetworkResult<InfoFilmCloud>>()

    val favoritesFilms = MutableSharedFlow<List<FilmUi>>()

    private val excetionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.d("MainViewModel", "Exception caught: $throwable")
    }
    private val scope = CoroutineScope(Dispatchers.IO + excetionHandler)

     fun getTopMovie() {
         scope.launch {
             try {
                 val pageFilms = movieApi.getTopFilms()
                 if (pageFilms.topFilms.isEmpty()) {
                     searchFilms.emit(NetworkResult.Error("Not found"))
                 } else {
                     cacheDataSource.getData().collect{
                         val compareList = itemsTopComparison.compare(pageFilms.topFilms)
                         topFilms.emit(NetworkResult.Success(compareList))
                     }
                 }
             } catch (e: Exception) {
                 topFilms.emit(NetworkResult.Error(e.message))
             }
         }
    }

    fun getSearchMovie(keywords: String) {
        scope.launch {
            try {
                val pageFilms = movieApi.getFilmsByKeyWords(keywords)
                if (pageFilms.searchFilms.isEmpty()) {
                    searchFilms.emit(NetworkResult.Error.NotFound("Film not found"))
                } else {
                    cacheDataSource.getData().collect{
                        val compare = itemsSearchComparison.compare(pageFilms.searchFilms)
                        searchFilms.emit(NetworkResult.Success(compare))
                    }
                }
            } catch (e: Exception) {
                searchFilms.emit(NetworkResult.Error(e.message))
            }
        }
    }

    fun getInfoAboutFilm(filmId: Int) {
        scope.launch {
            try {
                val infoFilmsResponse = movieApi.getInfoAboutFilmById(filmId)
                infoFilmCloud.emit(NetworkResult.Success(infoFilmsResponse))
            } catch (e: Exception) {
                infoFilmCloud.emit(NetworkResult.Error(e.message))
            }
        }
    }

    suspend fun getFavoriteFilms(): Flow<List<FilmCache>> {
        // val filmsFlow = MutableSharedFlow<List<FilmUi>>()
        //val sharedList = MutableLiveData<List<FilmUi>>()
        val data = cacheDataSource.getData()
        val uiFilms = arrayListOf<FilmUi>()
        // data.collect {
//                data.value!!.forEach { filmUi ->
//                    uiFilms.add(filmUi.map(Film.Mapper.ToUi()))
//                }
//                //favoritesFilms.emit(uiFilms)
//                sharedList.value = uiFilms
        //}
        return data
    }

    suspend fun addFilmsToFavorite(baseFilm: FilmBase) {
        cacheDataSource.addOrRemove(baseFilm)
    }
}

