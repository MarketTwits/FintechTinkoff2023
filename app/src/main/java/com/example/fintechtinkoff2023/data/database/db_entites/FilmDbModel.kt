package com.example.fintechtinkoff2023.data.database.db_entites

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites_films_lis")
data class FilmDbModel(
    @PrimaryKey
    val idFilm: Int,
    val filmCountries: List<CountryDbModel>,
    val filmGenre : List<GenreDbModel>,
    val description: String,
    val nameRu: String,
    val posterUrl: String,
    val year: Int,
    val timeAdded : Long
)