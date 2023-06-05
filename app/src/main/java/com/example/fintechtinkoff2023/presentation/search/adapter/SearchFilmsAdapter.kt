package com.example.fintechtinkoff2023.presentation.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.fintechtinkoff2023.databinding.FilmItemBinding
import com.example.fintechtinkoff2023.databinding.FilmItemNotFoundBinding
import com.example.fintechtinkoff2023.databinding.PopularFilmsErrorBinding
import com.example.fintechtinkoff2023.databinding.PopularFilmsLoadingBinding
import com.example.fintechtinkoff2023.domain.model.FilmUi
import com.example.fintechtinkoff2023.presentation.base.adapter.FilmsUiItemDiffCallback
import com.example.fintechtinkoff2023.presentation.popular.adapter.PopularFilmsAdapter
import com.example.fintechtinkoff2023.presentation.utils.adapterListener.ItemClick
import com.example.fintechtinkoff2023.presentation.utils.adapterListener.ItemLongClick
import com.example.fintechtinkoff2023.presentation.utils.adapterListener.Retry


class SearchFilmsAdapter(
    private val retry: Retry,
    private val onItemClicked: ItemClick,
    private val onItemLongClicked: ItemLongClick,
) : ListAdapter<FilmUi, PopularFilmsAdapter.PopularFilmsViewHolder>(FilmsUiItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularFilmsAdapter.PopularFilmsViewHolder {
        val favoriteBinding =
            FilmItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val failedBinding =
            PopularFilmsErrorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val loadingBinding =
            PopularFilmsLoadingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val filmNotFoundBinding
                = FilmItemNotFoundBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        val viewHolder = when (viewType) {
            0 -> PopularFilmsAdapter.PopularFilmsViewHolder.Favorite(favoriteBinding, onItemClicked, onItemLongClicked)
            1 -> PopularFilmsAdapter.PopularFilmsViewHolder.Base(favoriteBinding, onItemClicked, onItemLongClicked)
            2 -> PopularFilmsAdapter.PopularFilmsViewHolder.Fail(failedBinding, retry)
            3 -> PopularFilmsAdapter.PopularFilmsViewHolder.FilmNotFound(filmNotFoundBinding)
            else -> PopularFilmsAdapter.PopularFilmsViewHolder.Loading(loadingBinding)
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

    override fun onBindViewHolder(holder: PopularFilmsAdapter.PopularFilmsViewHolder, position: Int) {
        holder.bind(currentList[position])
    }
}