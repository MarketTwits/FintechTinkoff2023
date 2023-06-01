package com.example.fintechtinkoff2023.core

import android.content.Context
import com.example.fintechtinkoff2023.core.communication.NavigationCommunication
import com.example.fintechtinkoff2023.core.storage.RoomStorage
import com.example.fintechtinkoff2023.core.storage.SharedPreferencesStorage
import com.example.fintechtinkoff2023.core.wrappers.ManageResource
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
    private val sharedPreferencesStorage =
        SharedPreferencesStorage.Base(context.getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE))

    override fun storage(): SharedPreferencesStorage = sharedPreferencesStorage

    companion object {
        private const val STORAGE_NAME = "NEWS APP DATA"
    }

    override fun manageResource(): ManageResource = manageResource
    override fun database(): RoomStorage = RoomStorage.AppDatabase.getInstance(context)
    override fun filmsInteractor(): FilmInteract {
        val cacheDataSource = CacheDataSource.Base(database().filmDao())
        return FilmInteract.Base(
            cacheDataSource,
            FavoriteFilmsComparisonMapper.Base(),
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
    fun storage(): SharedPreferencesStorage
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

