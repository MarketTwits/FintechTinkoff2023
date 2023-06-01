package com.example.fintechtinkoff2023.presentation.popular.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.fintechtinkoff2023.domain.model.FilmUi

class PopularFilmsItemDiffCallback : DiffUtil.ItemCallback<FilmUi>() {
    override fun areItemsTheSame(oldItem: FilmUi, newItem: FilmUi): Boolean {
        return oldItem.filmId == newItem.filmId
    }

    override fun areContentsTheSame(oldItem: FilmUi, newItem: FilmUi): Boolean {
        return oldItem == newItem
    }
}