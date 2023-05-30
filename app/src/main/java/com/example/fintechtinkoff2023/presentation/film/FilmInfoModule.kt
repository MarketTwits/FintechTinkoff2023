package com.example.fintechtinkoff2023.presentation.film

import com.example.fintechtinkoff2023.core.Core
import com.example.fintechtinkoff2023.core.Module
import com.example.fintechtinkoff2023.data.database.CacheDataSource
import com.example.fintechtinkoff2023.data.network.FilmInteractor
import com.example.fintechtinkoff2023.data.network.FilmsCloudDataSource
import com.example.fintechtinkoff2023.data.network.TestRepository
import com.example.fintechtinkoff2023.data.network.mapper.FilmsCloudToDomainFilmMapper
import com.example.fintechtinkoff2023.data.network.retrofit.MakeService
import com.example.fintechtinkoff2023.domain.base_source.FavoriteFilmsComparisonMapper

class FilmInfoModule(private val core: Core) : Module<FilmInfoViewModel> {
    val cacheDataSource = CacheDataSource.Base(core.database().filmDao())
    override fun viewModel() = FilmInfoViewModel(
        core.filmsRepository(),
        FilmInteractor.Base(
            cacheDataSource,
            FavoriteFilmsComparisonMapper.Base(
                cacheDataSource
            ),
            TestRepository.Base(
                cacheDataSource,
                FilmsCloudDataSource.Base(
                    MakeService.Base().service()
                ),
                FilmsCloudToDomainFilmMapper.Base()
            )
        )
    )
}