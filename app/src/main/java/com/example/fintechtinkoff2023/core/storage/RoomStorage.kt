package com.example.fintechtinkoff2023.core.storage

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.fintechtinkoff2023.data.database.db_entites.FilmCache
import com.example.fintechtinkoff2023.data.database.room.FilmFavoritesDao

interface RoomStorage {
    fun filmDao(): FilmFavoritesDao

    @Database(entities = [FilmCache::class], version = 1, exportSchema = false)
    abstract class AppDatabase : RoomDatabase(), RoomStorage {
        companion object {
            private var db: AppDatabase? = null
            private const val DB_NAME = "main.db"
            private val LOCK = Any()

            fun getInstance(context: Context): AppDatabase {
                synchronized(LOCK) {
                    db?.let { return it }
                    val instance =
                        Room.databaseBuilder(
                            context,
                            AppDatabase::class.java,
                            DB_NAME
                        )
                            .fallbackToDestructiveMigration()
                            .build()
                    db = instance
                    return instance
                }
            }
        }
        abstract override fun filmDao(): FilmFavoritesDao
    }
}