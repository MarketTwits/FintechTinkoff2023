package com.example.fintechtinkoff2023.core

import android.content.Context
import com.example.fintechtinkoff2023.core.communication.NavigationCommunication
import com.example.fintechtinkoff2023.core.storage.RoomCacheDataSource
import com.example.fintechtinkoff2023.core.storage.SharedPreferencesStorage
import com.example.fintechtinkoff2023.core.wrappers.ManageResource

class Core(
    private val context: Context
) : ProvideStorage, ProvideManageResource, ProvideRoomDataBase {
    private val navigation = NavigationCommunication.Base()
    private val manageResource = ManageResource.Base(context)
    private val sharedPreferencesStorage =
        SharedPreferencesStorage.Base(context.getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE))

    override fun storage(): SharedPreferencesStorage = sharedPreferencesStorage

    companion object {
        private const val STORAGE_NAME = "NEWS APP DATA"
    }
    override fun manageResource(): ManageResource = manageResource
    override fun database(): RoomCacheDataSource = RoomCacheDataSource.Base(context)
}

interface ProvideStorage {
    fun storage(): SharedPreferencesStorage
}

interface ProvideManageResource {
    fun manageResource(): ManageResource
}
interface ProvideRoomDataBase{
    fun database() : RoomCacheDataSource
}
