package com.example.fintechtinkoff2023.data.database.room

import androidx.room.*
import com.example.fintechtinkoff2023.data.database.db_entites.FilmCache
import kotlinx.coroutines.flow.Flow


@Dao
interface FilmFavoritesDao {
    @Query("SELECT * FROM film_info_cached ORDER BY id DESC")
    fun getFavoritesFilmsList(): Flow<List<FilmCache>>
    @Query("SELECT * FROM film_info_cached WHERE filmId = :id")
    fun getFilmInfoById(id: String): FilmCache?
    @Insert
    suspend fun insertFavoriteFilmInfo(film : FilmCache)
    @Query("DELETE FROM film_info_cached WHERE filmId = :filmId")
    suspend fun deleteFavoriteFilmInfo(filmId: Int)

}