package com.example.fintechtinkoff2023

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.example.fintechtinkoff2023.core.Core
import com.example.fintechtinkoff2023.core.sl.DependencyContainer
import com.example.fintechtinkoff2023.core.sl.ProvideViewModel
import com.example.fintechtinkoff2023.core.sl.ViewModelsFactory
import com.example.fintechtinkoff2023.data.FilmsCloudDataSource
import com.example.fintechtinkoff2023.data.database.CacheDataSource
import com.example.fintechtinkoff2023.data.mapper.FilmBaseToCacheMapper
import com.example.fintechtinkoff2023.data.mapper.FilmsCacheToDomainFilmMapper
import com.example.fintechtinkoff2023.data.network.mapper.FilmsCloudToDomainFilmMapper
import com.example.fintechtinkoff2023.data.network.retrofit.MakeService
import com.example.fintechtinkoff2023.data.worker.ProvidePeriodicInteractor
import com.example.fintechtinkoff2023.domain.FilmInteract
import com.example.fintechtinkoff2023.domain.FilmRepository
import com.example.fintechtinkoff2023.domain.error.ErrorTypeDomainMapper
import com.example.fintechtinkoff2023.domain.mapper.ErrorTypeDomainToUiMapper
import com.example.fintechtinkoff2023.domain.mapper.FavoriteFilmsComparisonMapper
import com.example.fintechtinkoff2023.domain.mapper.FilmUiToDomainFilmMapper
import com.example.fintechtinkoff2023.presentation.screens.base.module.BaseModule

class FintechApp : Application(), ProvideViewModel,
    ProvidePeriodicInteractor
{
    override fun <T : ViewModel> viewModel(owner: ViewModelStoreOwner, className: Class<T>): T {
        return ViewModelProvider(owner, viewModelFactory)[className]
    }
     private lateinit var viewModelFactory: ViewModelsFactory
     private lateinit var core: Core
     private lateinit var interactor : FilmInteract
    override fun onCreate() {
        super.onCreate()
        core = Core(this)
        viewModelFactory = ViewModelsFactory(
            DependencyContainer.Base(core)
        )
        interactor =  BaseModule.Base(core).provideInteractor()
    }
    override fun providePeriodicInteractor(): FilmInteract = interactor
}

