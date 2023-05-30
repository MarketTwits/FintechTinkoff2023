package com.example.fintechtinkoff2023.core

import android.content.Context
import com.example.fintechtinkoff2023.data.database.CacheDataSource
import com.example.fintechtinkoff2023.data.network.retrofit.MakeService
import com.example.fintechtinkoff2023.domain.FilmsRepositoryImpl
import com.example.fintechtinkoff2023.domain.base_source.ItemsSearchComparison
import com.example.fintechtinkoff2023.domain.base_source.ItemsTopComparison

class Core(
    private val context: Context
) : ProvideStorage, ProvideManageResource, ProvideRoomDataBase, ProvideFilmsRepository {
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
    override fun filmsRepository() = FilmsRepositoryImpl(
        CacheDataSource.Base(
            database().filmDao()
        ),
        MakeService.Base().service(),
        ItemsTopComparison.TopFilmCompare(
            CacheDataSource.Base(
                database().filmDao()
            )
        ),
        ItemsSearchComparison.SearchFilmCompare(
            CacheDataSource.Base(
                database().filmDao()
            )
        )
    )

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

interface ProvideFilmsRepository {
    fun filmsRepository(): FilmsRepositoryImpl
}

