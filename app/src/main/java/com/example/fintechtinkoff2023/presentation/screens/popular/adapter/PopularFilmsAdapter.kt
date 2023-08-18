package com.example.fintechtinkoff2023.presentation.screens.popular.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.fintechtinkoff2023.core.view.BaseFilmsViewHolder
import com.example.fintechtinkoff2023.databinding.FilmItemBinding
import com.example.fintechtinkoff2023.databinding.FilmItemNotFoundBinding
import com.example.fintechtinkoff2023.databinding.PopularFilmsErrorBinding
import com.example.fintechtinkoff2023.databinding.PopularFilmsLoadingBinding
import com.example.fintechtinkoff2023.presentation.models.FilmUi
import com.example.fintechtinkoff2023.presentation.screens.base.adapter.FilmsUiItemDiffCallback
import com.example.fintechtinkoff2023.presentation.utils.adapterListener.ItemActions


class PopularFilmsAdapter(
    private val mutable : ItemActions.Mutable
) : ListAdapter<FilmUi, BaseFilmsViewHolder>(FilmsUiItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseFilmsViewHolder {

        val favoriteBinding =
            FilmItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val failedBinding =
            PopularFilmsErrorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val loadingBinding =
            PopularFilmsLoadingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val filmNotFoundBinding
        = FilmItemNotFoundBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        val viewHolder = when (viewType) {
            0 -> BaseFilmsViewHolder.Favorite(favoriteBinding, mutable)
            1 -> BaseFilmsViewHolder.Base(favoriteBinding, mutable)
            2 -> BaseFilmsViewHolder.Fail(failedBinding, mutable)
            3 -> BaseFilmsViewHolder.FilmNotFound(filmNotFoundBinding)
            else -> BaseFilmsViewHolder.Loading(loadingBinding)
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: BaseFilmsViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    override fun getItemViewType(position: Int): Int = when (currentList[position]) {
        is FilmUi.Favorite -> 0
        is FilmUi.Base -> 1
        is FilmUi.Failed -> 2
        is FilmUi.Failed.FilmNotFound -> 3
        is FilmUi.Progress -> 4
    }
}