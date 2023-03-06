package com.example.fintechtinkoff2023.presentation.popular.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.fintechtinkoff2023.data.network.model.page_film.TopFilm

class TopFilmsItemDiffCallback : DiffUtil.ItemCallback<TopFilm>() {
    override fun areItemsTheSame(oldItem: TopFilm, newItem: TopFilm): Boolean {
        return oldItem.filmId == newItem.filmId
    }

    override fun areContentsTheSame(oldItem: TopFilm, newItem: TopFilm): Boolean {
        return oldItem == newItem
    }
}