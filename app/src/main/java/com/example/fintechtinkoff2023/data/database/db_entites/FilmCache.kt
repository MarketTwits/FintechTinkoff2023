package com.example.fintechtinkoff2023.data.database.db_entites

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.fintechtinkoff2023.domain.model.Film

@Entity(tableName = "favorites_films_list")
class FilmCache(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val filmId : Int,
    val nameRu: String ,
    val posterUrl: String,
    val year: String,
) : Film{
    override suspend fun <T> map(mapper: Film.Mapper<T>): T =
        mapper.map(filmId, nameRu, posterUrl, year)
}