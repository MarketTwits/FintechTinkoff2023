package com.example.fintechtinkoff2023

import android.app.Application
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.example.fintechtinkoff2023.core.Core
import com.example.fintechtinkoff2023.core.DependencyContainer
import com.example.fintechtinkoff2023.core.ProvideViewModel
import com.example.fintechtinkoff2023.core.ViewModelsFactory
import com.example.fintechtinkoff2023.data.database.room.AppDatabase
import com.example.fintechtinkoff2023.data.network.retrofit.KinoPoiskApi
import com.example.fintechtinkoff2023.domain.FilmsRepositoryImpl
import com.example.fintechtinkoff2023.data.database.CacheDataSource
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

class FintechApp : Application(), ProvideViewModel {
    override fun <T : ViewModel> viewModel(owner: ViewModelStoreOwner, className: Class<T>): T {
        return ViewModelProvider(owner, viewModelFactory)[className]
    }

    private lateinit var viewModelFactory: ViewModelsFactory

    override fun onCreate() {
        super.onCreate()

        viewModelFactory = ViewModelsFactory(
            DependencyContainer.Base(Core(this))
        )

    }
}

