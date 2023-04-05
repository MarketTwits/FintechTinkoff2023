package com.example.fintechtinkoff2023.data.database.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.fintechtinkoff2023.data.database.db_entites.FilmDbModel

@Dao
interface FilmFavoritesDao {
    @Query("SELECT * FROM favorites_films_lis ORDER BY timeAdded DESC")
    fun getFavoritesFilmsList(): LiveData<List<FilmDbModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoritesFilm(film: FilmDbModel)
}