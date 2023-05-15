package com.example.fintechtinkoff2023

import android.app.Application
import com.example.fintechtinkoff2023.data.database.room.AppDatabase
import com.example.fintechtinkoff2023.data.network.retrofit.KinoPoiskApi
import com.example.fintechtinkoff2023.data.network.retrofit.RetrofitInstance
import com.example.fintechtinkoff2023.domain.FilmsRepositoryImpl
import com.example.fintechtinkoff2023.domain.base_source.CacheDataSource
import com.example.fintechtinkoff2023.domain.base_source.ItemsSearchComparison
import com.example.fintechtinkoff2023.domain.base_source.ItemsTopComparison
import com.example.fintechtinkoff2023.presentation.favorites.FavoritesFilmViewModel
import com.example.fintechtinkoff2023.presentation.film.FilmInfoViewModel
import com.example.fintechtinkoff2023.presentation.popular.PopularFilmsViewModel
import com.example.fintechtinkoff2023.presentation.search.SearchFilmsViewModel
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

class FintechApp : Application() {

    private companion object {
        const val BASE_URL = "https://kinopoiskapiunofficial.tech/api/"
    }
    lateinit var favoritesFilmViewModel: FavoritesFilmViewModel
    lateinit var popularFilmsViewModel: PopularFilmsViewModel
    lateinit var searchFilmsViewModel: SearchFilmsViewModel
    lateinit var filmInfoViewModel : FilmInfoViewModel
    override fun onCreate() {
        super.onCreate()

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)
        .cache(Cache(
            directory = File(this.cacheDir, "http_cache"),
            // $0.05 worth of phone storage in 2020
            maxSize = 50L * 1024L * 1024L // 50 MiB
        ))
        .build()

        val retrofit =  Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()

        val apiService = retrofit.create(KinoPoiskApi::class.java)

        val filmsRepositoryImpl = FilmsRepositoryImpl(
            CacheDataSource.Base(
                AppDatabase.getInstance(this).filmDao()
            ),
            apiService,
            ItemsTopComparison.TopFilmCompare( CacheDataSource.Base(
                AppDatabase.getInstance(this).filmDao()
            )),
            ItemsSearchComparison.SearchFilmCompare(CacheDataSource.Base(AppDatabase.getInstance(this).filmDao()))
        )
        favoritesFilmViewModel = FavoritesFilmViewModel(
           filmsRepositoryImpl
        )
        popularFilmsViewModel = PopularFilmsViewModel(
            filmsRepositoryImpl
        )
        searchFilmsViewModel = SearchFilmsViewModel(
            filmsRepositoryImpl
        )
        filmInfoViewModel = FilmInfoViewModel(
            filmsRepositoryImpl
        )
    }
}