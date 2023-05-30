package com.example.fintechtinkoff2023.presentation.search

import com.example.fintechtinkoff2023.core.Core
import com.example.fintechtinkoff2023.core.Module
import com.example.fintechtinkoff2023.data.database.CacheDataSource
import com.example.fintechtinkoff2023.data.network.FilmInteractor
import com.example.fintechtinkoff2023.data.network.FilmsCloudDataSource
import com.example.fintechtinkoff2023.data.network.TestRepository
import com.example.fintechtinkoff2023.data.network.mapper.FilmsCloudToDomainFilmMapper
import com.example.fintechtinkoff2023.data.network.retrofit.MakeService
import com.example.fintechtinkoff2023.domain.base_source.FavoriteFilmsComparisonMapper

class SearchModule(private val core: Core) : Module<SearchFilmsViewModel> {
    override fun viewModel() = SearchFilmsViewModel(

        FilmInteractor.Base(
            CacheDataSource.Base(core.database().filmDao()),
            FavoriteFilmsComparisonMapper.Base(
                CacheDataSource.Base(core.database().filmDao())
            ),
            TestRepository.Base(
                CacheDataSource.Base(core.database().filmDao()),
                FilmsCloudDataSource.Base(
                    MakeService.Base().service()
                ),
                FilmsCloudToDomainFilmMapper.Base()
            )
        ),
        core.filmsRepository(),
    )
}