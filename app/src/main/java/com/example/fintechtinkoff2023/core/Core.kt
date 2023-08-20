package com.example.fintechtinkoff2023.core
import android.content.Context
import com.example.fintechtinkoff2023.FintechApp
import com.example.fintechtinkoff2023.core.storage.RoomCacheDataSource
import com.example.fintechtinkoff2023.core.storage.SharedPreferencesStorage
import com.example.fintechtinkoff2023.core.wrappers.ManageResource
import com.example.fintechtinkoff2023.core.wrappers.WorkManagerWrapper
import com.example.fintechtinkoff2023.data.worker.ProvidePeriodicInteractor


class Core(
    private val context: Context
) : ProvideStorage, ProvideManageResource, ProvideRoomDataBase, ProvideWorkManagerWrapper, ProvidePeriodicInteractor {
    private val manageResource = ManageResource.Base(context)
    private val sharedPreferencesStorage =
        SharedPreferencesStorage.Base(context.getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE))
    override fun storage(): SharedPreferencesStorage = sharedPreferencesStorage
    override fun manageResource(): ManageResource = manageResource
    override fun database(): RoomCacheDataSource = RoomCacheDataSource.Base(context)

    companion object {
        private const val STORAGE_NAME = "NEWS APP DATA"
    }
    override fun provideWorkManagerWrapper() = WorkManagerWrapper.Base(context)
    override fun providePeriodicInteractor() = (context as FintechApp).providePeriodicInteractor()
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
interface ProvideWorkManagerWrapper{
    fun provideWorkManagerWrapper() : WorkManagerWrapper
}
