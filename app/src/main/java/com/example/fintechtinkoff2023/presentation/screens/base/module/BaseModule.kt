package com.example.fintechtinkoff2023.presentation.screens.base.module

import com.example.fintechtinkoff2023.core.Core
import com.example.fintechtinkoff2023.data.database.CacheDataSource
import com.example.fintechtinkoff2023.data.FilmsCloudDataSource
import com.example.fintechtinkoff2023.data.mapper.FilmBaseToCacheMapper
import com.example.fintechtinkoff2023.data.mapper.FilmsCacheToDomainFilmMapper
import com.example.fintechtinkoff2023.data.network.mapper.FilmsCloudToDomainFilmMapper
import com.example.fintechtinkoff2023.data.network.retrofit.MakeService
import com.example.fintechtinkoff2023.domain.FilmInteract
import com.example.fintechtinkoff2023.domain.FilmRepository
import com.example.fintechtinkoff2023.domain.error.ErrorTypeDomainMapper
import com.example.fintechtinkoff2023.domain.mapper.ErrorTypeDomainToUiMapper
import com.example.fintechtinkoff2023.domain.mapper.FavoriteFilmsComparisonMapper
import com.example.fintechtinkoff2023.domain.mapper.FilmUiToDomainFilmMapper

interface BaseModule {
     fun provideInteractor() : FilmInteract
    abstract class Abstract(private val core: Core) : BaseModule {
        override  fun provideInteractor(): FilmInteract {
                val cacheDataSource = CacheDataSource.Base(
                    FilmBaseToCacheMapper.Base(),
                    core.database().favoritesDao()
                )
                return FilmInteract.Base(
                    cacheDataSource,
                    FavoriteFilmsComparisonMapper.Base(),
                    ErrorTypeDomainToUiMapper.Base(core.manageResource()),
                    FilmUiToDomainFilmMapper.Base(),
                    FilmRepository.Base(
                        cacheDataSource,
                        FilmsCloudDataSource.Base(
                            MakeService.Base().service()
                        ),
                        ErrorTypeDomainMapper.Base(),
                        FilmsCloudToDomainFilmMapper.Base(),
                        FilmsCacheToDomainFilmMapper.Base()
                    )
                )
        }
    }
    class Base(core : Core) : Abstract(core)
}