package com.example.fintechtinkoff2023.data.database.room

import androidx.room.TypeConverter
import com.example.fintechtinkoff2023.data.network.models.base_film_model.Country
import com.example.fintechtinkoff2023.data.network.models.base_film_model.Genre
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

interface RoomConverter {
    class CountryListConverter {
        @TypeConverter
        fun fromCountryList(countryList: List<Country>): String {
            val gson = Gson()
            return gson.toJson(countryList)
        }

        @TypeConverter
        fun toCountryList(countryListString: String): List<Country> {
            val gson = Gson()
            val countryListType = object : TypeToken<List<Country>>() {}.type
            return gson.fromJson(countryListString, countryListType)
        }
    }

    class GenreListConverter {
        @TypeConverter
        fun fromGenreList(genreList: List<Genre>): String {
            val gson = Gson()
            return gson.toJson(genreList)
        }

        @TypeConverter
        fun toGenreList(genreListString: String): List<Genre> {
            val gson = Gson()
            val genreListType = object : TypeToken<List<Genre>>() {}.type
            return gson.fromJson(genreListString, genreListType)
        }
    }
}