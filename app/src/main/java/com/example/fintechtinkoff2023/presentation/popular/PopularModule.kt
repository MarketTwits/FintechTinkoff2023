package com.example.fintechtinkoff2023.presentation.popular

import com.example.fintechtinkoff2023.core.Core
import com.example.fintechtinkoff2023.core.Module
import com.example.fintechtinkoff2023.data.database.CacheDataSource
import com.example.fintechtinkoff2023.domain.FilmInteract
import com.example.fintechtinkoff2023.data.network.FilmsCloudDataSource
import com.example.fintechtinkoff2023.domain.FilmRepository
import com.example.fintechtinkoff2023.data.network.mapper.FilmsCloudToDomainFilmMapper
import com.example.fintechtinkoff2023.data.network.retrofit.MakeService
import com.example.fintechtinkoff2023.domain.FavoriteFilmsComparisonMapper

class PopularModule(
    private val core: Core,
) : Module<PopularFilmsViewModel> {
    override fun viewModel(): PopularFilmsViewModel {
         return PopularFilmsViewModel(
            core.filmsInteractor()
        )
    }
}