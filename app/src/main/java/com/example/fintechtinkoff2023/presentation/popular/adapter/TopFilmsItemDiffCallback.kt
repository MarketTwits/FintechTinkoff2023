package com.example.fintechtinkoff2023.presentation.popular.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.fintechtinkoff2023.data.network.model.page_film.Film

class TopFilmsItemDiffCallback : DiffUtil.ItemCallback<Film>() {
    override fun areItemsTheSame(oldItem: Film, newItem: Film): Boolean {
        return oldItem.filmId == newItem.filmId
    }

    override fun areContentsTheSame(oldItem: Film, newItem: Film): Boolean {
        return oldItem == newItem
    }
}