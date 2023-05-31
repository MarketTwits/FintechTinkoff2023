package com.example.fintechtinkoff2023.presentation.film

import com.example.fintechtinkoff2023.core.Core
import com.example.fintechtinkoff2023.core.Module
import com.example.fintechtinkoff2023.data.database.CacheDataSource
import com.example.fintechtinkoff2023.domain.FilmInteract
import com.example.fintechtinkoff2023.data.network.FilmsCloudDataSource
import com.example.fintechtinkoff2023.domain.FilmRepository
import com.example.fintechtinkoff2023.data.network.mapper.FilmsCloudToDomainFilmMapper
import com.example.fintechtinkoff2023.data.network.retrofit.MakeService
import com.example.fintechtinkoff2023.domain.FavoriteFilmsComparisonMapper

class FilmInfoModule(private val core: Core) : Module<FilmInfoViewModel> {
    override fun viewModel() = FilmInfoViewModel(
        core.filmsInteractor()
    )
}