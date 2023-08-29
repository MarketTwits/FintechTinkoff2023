package com.example.fintechtinkoff2023.presentation.screens.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.fintechtinkoff2023.core.view.BaseFilmsViewHolder
import com.example.fintechtinkoff2023.databinding.FilmErrorBinding
import com.example.fintechtinkoff2023.databinding.FilmItemBinding
import com.example.fintechtinkoff2023.databinding.FilmItemNotFoundBinding
import com.example.fintechtinkoff2023.databinding.FilmLoadingBinding
import com.example.fintechtinkoff2023.presentation.models.FilmUi
import com.example.fintechtinkoff2023.presentation.screens.base.adapter.FilmsUiItemDiffCallback
import com.example.fintechtinkoff2023.presentation.utils.adapterListener.ItemActions


class SearchFilmsAdapter(
    private val action: ItemActions.Mutable
) : ListAdapter<FilmUi, BaseFilmsViewHolder>(FilmsUiItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseFilmsViewHolder {
        val favoriteBinding =
            FilmItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val failedBinding =
            FilmErrorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val loadingBinding =
            FilmLoadingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val filmNotFoundBinding =
            FilmItemNotFoundBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        val viewHolder = when (viewType) {
            0 -> BaseFilmsViewHolder.Favorite(favoriteBinding, action)
            1 -> BaseFilmsViewHolder.Base(favoriteBinding, action)
            2 -> BaseFilmsViewHolder.Fail(failedBinding, action)
            3 -> BaseFilmsViewHolder.FilmNotFound(filmNotFoundBinding)
            else -> BaseFilmsViewHolder.Loading(loadingBinding)
        }
        return viewHolder
    }

    override fun getItemViewType(position: Int): Int = when (currentList[position]) {
        is FilmUi.Favorite -> 0
        is FilmUi.Base -> 1
        is FilmUi.Failed -> 2
        is FilmUi.Failed.FilmNotFound -> 3
        is FilmUi.Progress -> 4
    }

    override fun onBindViewHolder(holder: BaseFilmsViewHolder, position: Int) {
        holder.bind(currentList[position])
    }
}