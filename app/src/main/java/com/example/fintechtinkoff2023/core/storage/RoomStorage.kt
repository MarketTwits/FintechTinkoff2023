package com.example.fintechtinkoff2023.core.storage

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import com.example.fintechtinkoff2023.data.database.db_entites.FilmCache
import com.example.fintechtinkoff2023.data.database.room.FilmFavoritesDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
@Database(entities = [FilmCache::class], version = 1, exportSchema = false)
abstract class RoomSchema : RoomDatabase() {
    abstract fun filmDao(): FilmFavoritesDao
}
interface RoomCacheDataSource : FilmsFavoriteDataSource {

    abstract class Abstract(
        private val context: Context,
        private val dataBaseName: String,
    ) : RoomCacheDataSource {
        override fun favoritesDao(): FilmFavoritesDao {
            return getInstance(context, RoomSchema::class.java, dataBaseName).filmDao()
        }
        companion object {
            private var db: RoomSchema? = null
            fun getInstance(context: Context, schema : Class<RoomSchema>, dataBaseName: String): RoomSchema {
                synchronized(lock) {
                    db?.let { return it }
                    val instance =
                        databaseBuilder(
                            context,
                            schema,
                            dataBaseName
                        )
                            .fallbackToDestructiveMigration()
                            .build()
                    db = instance
                    return instance
                }
            }
            private val lock = Object()
        }
    }

    class Base(context: Context) : Abstract(context, "database.db")
}
interface FilmsFavoriteDataSource {
    fun favoritesDao(): FilmFavoritesDao
}

