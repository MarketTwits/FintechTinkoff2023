package com.example.fintechtinkoff2023.presentation.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import com.example.fintechtinkoff2023.databinding.FilmItemBinding
import com.example.fintechtinkoff2023.presentation.popular.adapter.SearchFilmsItemDiffCallback
import com.example.fintechtinkoff2023.presentation.popular.adapter.SearchFilmsViewHolder
import com.example.fintechtinkoff2023.data.network.model.search_films.Film


class SearchFilmsAdapter : ListAdapter<Film, SearchFilmsViewHolder>(SearchFilmsItemDiffCallback()) {

    var onFeelingsItemClickListener: ((Film) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchFilmsViewHolder {

        val binding =
            FilmItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return SearchFilmsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchFilmsViewHolder, position: Int) {
        val binding = holder.binding
        val element = getItem(position)

        binding.root.setOnClickListener {
            onFeelingsItemClickListener?.invoke(element)
        }
        binding.tvFilmTitle.text = element.nameRu
        binding.tvGenreAndYearOfRelease.text = element.year
        Glide
            .with(binding.imFilm)
            .load(element.posterUrl)
            .into(binding.imFilm)
    }
}