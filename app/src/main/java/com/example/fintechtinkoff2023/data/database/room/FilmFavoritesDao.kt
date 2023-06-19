package com.example.fintechtinkoff2023.data.database.room

import androidx.room.*
import com.example.fintechtinkoff2023.data.database.db_entites.FilmCache
import com.example.fintechtinkoff2023.data.database.db_entites.FilmInfoCache
import kotlinx.coroutines.flow.Flow


@Dao
interface FilmFavoritesDao {
    @Query("SELECT * FROM film_popular_cached ORDER BY id ASC")
    suspend fun getPopularFilmsList(): List<FilmCache>
    @Query("SELECT * FROM film_info_cached ORDER BY id DESC")
    fun getFavoritesFilmsList(): Flow<List<FilmInfoCache>>
    @Query("SELECT * FROM film_info_cached WHERE filmId = :id")
    fun getFilmInfoById(id: String): FilmInfoCache?
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPopularFilms(films : List<FilmCache>)
    @Insert
    suspend fun insertFavoriteFilmInfo(film : FilmInfoCache)
    @Query("DELETE FROM film_info_cached WHERE filmId = :filmId")
    suspend fun deleteFavoriteFilmInfo(filmId: Int)
    @Query("SELECT * FROM film_popular_cached WHERE filmId = :filmId")
    suspend fun getTopFilmById(filmId : Int) : FilmCache?
}