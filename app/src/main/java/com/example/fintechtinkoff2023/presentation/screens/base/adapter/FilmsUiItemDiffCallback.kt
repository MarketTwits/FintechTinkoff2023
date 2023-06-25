package com.example.fintechtinkoff2023.presentation.screens.base.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.fintechtinkoff2023.presentation.models.FilmUi

class FilmsUiItemDiffCallback : DiffUtil.ItemCallback<FilmUi>() {
    override fun areItemsTheSame(oldItem: FilmUi, newItem: FilmUi): Boolean {
        return oldItem.filmId == newItem.filmId
    }

    override fun areContentsTheSame(oldItem: FilmUi, newItem: FilmUi): Boolean {
        return oldItem == newItem
    }
}