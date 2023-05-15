package com.example.fintechtinkoff2023.data.database.room

import androidx.room.*
import com.example.fintechtinkoff2023.data.database.db_entites.FilmCache
import kotlinx.coroutines.flow.Flow

@Dao
interface FilmFavoritesDao {
    @Query("SELECT * FROM favorites_films_list ORDER BY id DESC")
    fun getFavoritesFilmsList(): Flow<List<FilmCache>>

    @Query("SELECT * FROM favorites_films_list WHERE filmId = :id")
    fun getFilmById(id: String): FilmCache?

    @Insert
    suspend fun insertFavoritesFilm(film: FilmCache)

    @Query("DELETE FROM favorites_films_list WHERE filmId = :filmId")
    suspend fun deleteFavoriteFilm(filmId: Int)
}