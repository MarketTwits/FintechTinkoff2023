package com.example.fintechtinkoff2023.data.database.db_entites

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.fintechtinkoff2023.data.database.room.RoomConverter
import com.example.fintechtinkoff2023.domain.models.Film

@TypeConverters(RoomConverter.PopularFilmConverter::class)
@Entity(tableName = "film_popular_cached")
data class FilmCache(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val filmId: Int = 0,
    val nameRu: String = "",
    val posterUrl: String = "",
    val year: String = "",
) : Film {
    override suspend fun <T> map(mapper: Film.Mapper<T>): T =
        mapper.map(filmId, nameRu, posterUrl, year)
}