package com.example.fintechtinkoff2023.presentation.popular.adapter

import androidx.recyclerview.widget.DiffUtil

import com.example.fintechtinkoff2023.data.network.model.search_films.SearchFilm

class SearchFilmsItemDiffCallback : DiffUtil.ItemCallback<SearchFilm>() {
    override fun areItemsTheSame(oldItem: SearchFilm, newItem: SearchFilm): Boolean {
        return oldItem.filmId == newItem.filmId
    }

    override fun areContentsTheSame(oldItem: SearchFilm, newItem: SearchFilm): Boolean {
        return oldItem == newItem
    }
}