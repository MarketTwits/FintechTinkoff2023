package com.example.fintechtinkoff2023.data.database.db_entites

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.fintechtinkoff2023.data.database.room.RoomConverter
import com.example.fintechtinkoff2023.data.network.models.base_film_model.Country
import com.example.fintechtinkoff2023.data.network.models.base_film_model.Genre
import com.example.fintechtinkoff2023.domain.models.Film
import com.example.fintechtinkoff2023.domain.models.FilmInfo

@Entity(tableName = "film_info_cached")
@TypeConverters(
    RoomConverter.CountryListConverter::class,
    RoomConverter.GenreListConverter::class
)
class FilmCache(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val filmId: Int = 0,
    val name: String = "",
    val posterUrl: String = "",
    val description: String = "",
    val year : String = "",
    val country: List<Country> = emptyList(),
    val genres: List<Genre> = emptyList(),
) : Film, FilmInfo {
    override suspend fun <T> map(mapper: FilmInfo.Mapper<T>): T =
        mapper.map(filmId, name, posterUrl, description, year, country, genres)

    override suspend fun <T> map(mapper: Film.Mapper<T>): T {
        return mapper.map(filmId, name, posterUrl, year)
    }

}