package com.example.fintechtinkoff2023.presentation.popular.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import com.example.fintechtinkoff2023.data.network.model.page_film.TopFilm
import com.example.fintechtinkoff2023.databinding.FilmItemBinding

class TopFilmsAdapter : ListAdapter<TopFilm,TopFilmsViewHolder>(TopFilmsItemDiffCallback()) {

    var onFilmItemClickListener: ((TopFilm) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopFilmsViewHolder {

        val binding =
            FilmItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return TopFilmsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TopFilmsViewHolder, position: Int) {
        val binding = holder.binding
        val element = getItem(position)

        binding.root.setOnClickListener {
            onFilmItemClickListener?.invoke(element)
        }
        binding.tvFilmTitle.text = element.nameRu
        binding.tvGenreAndYearOfRelease.text = element.year
        Glide
            .with(binding.imFilm)
            .load(element.posterUrl)
            .into(binding.imFilm)
    }
}