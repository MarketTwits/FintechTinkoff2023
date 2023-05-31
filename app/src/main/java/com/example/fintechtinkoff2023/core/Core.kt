package com.example.fintechtinkoff2023.core

import android.content.Context
import com.example.fintechtinkoff2023.data.database.CacheDataSource
import com.example.fintechtinkoff2023.data.network.FilmsCloudDataSource
import com.example.fintechtinkoff2023.data.network.mapper.FilmsCloudToDomainFilmMapper
import com.example.fintechtinkoff2023.data.network.retrofit.MakeService
import com.example.fintechtinkoff2023.domain.FavoriteFilmsComparisonMapper
import com.example.fintechtinkoff2023.domain.FilmInteract
import com.example.fintechtinkoff2023.domain.FilmRepository
import com.example.fintechtinkoff2023.domain.error.ErrorTypeDomainMapper
import com.example.fintechtinkoff2023.domain.mapper.ErrorTypeDomainToUiMapper
import com.example.fintechtinkoff2023.domain.mapper.FilmUiToDomainFilmMapper

class Core(
    private val context: Context
) : ProvideStorage, ProvideManageResource, ProvideRoomDataBase, ProvideInteract {
    private val navigation = NavigationCommunication.Base()
    private val manageResource = ManageResource.Base(context)
    private val storage =
        Storage.Base(context.getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE))

    override fun storage(): Storage = storage

    companion object {
        private const val STORAGE_NAME = "NEWS APP DATA"
    }

    override fun manageResource(): ManageResource = manageResource
    override fun database(): RoomStorage = RoomStorage.AppDatabase.getInstance(context)
    override fun filmsInteractor(): FilmInteract {
        val cacheDataSource = CacheDataSource.Base(database().filmDao())
        return FilmInteract.Base(
            cacheDataSource,
            FavoriteFilmsComparisonMapper.Base(
                cacheDataSource
            ),
            ErrorTypeDomainToUiMapper.Base(manageResource),
            FilmUiToDomainFilmMapper.Base(),
            FilmRepository.Base(
                cacheDataSource,
                FilmsCloudDataSource.Base(
                    MakeService.Base().service()
                ),
                ErrorTypeDomainMapper.Base(),
                FilmsCloudToDomainFilmMapper.Base()
            )
        )

    }
}

interface ProvideStorage {
    fun storage(): Storage
}

interface ProvideManageResource {
    fun manageResource(): ManageResource
}

interface ProvideRoomDataBase {
    fun database(): RoomStorage
}

interface ProvideInteract {
    fun filmsInteractor(): FilmInteract
}

