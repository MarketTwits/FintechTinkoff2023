package com.example.fintechtinkoff2023.presentation.screens.favorites.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fintechtinkoff2023.databinding.FilmItemBinding
import com.example.fintechtinkoff2023.presentation.models.FilmUi
import com.example.fintechtinkoff2023.presentation.screens.base.adapter.FilmsUiItemDiffCallback
import com.example.fintechtinkoff2023.presentation.utils.adapterListener.ItemActions

class FavoriteFilmsAdapter(
    private val action : ItemActions.MutableWithoutRetry,
) : ListAdapter<FilmUi, FavoriteFilmsViewHolder>(FilmsUiItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteFilmsViewHolder {
        val binding = FilmItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteFilmsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteFilmsViewHolder, position: Int) {
        val binding = holder.binding
        val item = getItem(position)
        binding.root.setOnClickListener {
            action.onClick(getItem(position))
        }
        binding.root.setOnLongClickListener {
            action.onLongClick(getItem(position))
            true
        }
        binding.tvFilmTitle.text = item.nameRu
        binding.tvGenreAndYearOfRelease.text = item.year
        binding.imStar.setImageResource(item.iconResId)
        Glide
            .with(binding.imFilm)
            .load(item.posterUrl)
            .into(binding.imFilm)
    }
}

class FavoriteFilmsViewHolder(val binding: FilmItemBinding) :
    RecyclerView.ViewHolder(binding.root)

