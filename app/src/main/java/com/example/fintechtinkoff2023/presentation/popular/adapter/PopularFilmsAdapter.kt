package com.example.fintechtinkoff2023.presentation.popular.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fintechtinkoff2023.databinding.FilmItemBinding
import com.example.fintechtinkoff2023.databinding.FilmItemNotFoundBinding
import com.example.fintechtinkoff2023.databinding.PopularFilmsErrorBinding
import com.example.fintechtinkoff2023.databinding.PopularFilmsLoadingBinding
import com.example.fintechtinkoff2023.domain.model.FilmUi
import com.example.fintechtinkoff2023.presentation.utils.adapterListener.ItemClick
import com.example.fintechtinkoff2023.presentation.utils.adapterListener.ItemLongClick
import com.example.fintechtinkoff2023.presentation.utils.adapterListener.Retry

class PopularFilmsAdapter(
    private val retry: Retry,
    private val onItemClicked: ItemClick,
    private val onItemLongClicked: ItemLongClick
) : ListAdapter<FilmUi, PopularFilmsAdapter.PopularFilmsViewHolder>(PopularFilmsItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularFilmsViewHolder {

        val favoriteBinding =
            FilmItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val failedBinding =
            PopularFilmsErrorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val loadingBinding =
            PopularFilmsLoadingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val filmNotFoundBinding
        = FilmItemNotFoundBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        val viewHolder = when (viewType) {
            0 -> PopularFilmsViewHolder.Favorite(favoriteBinding, onItemClicked, onItemLongClicked)
            1 -> PopularFilmsViewHolder.Base(favoriteBinding, onItemClicked, onItemLongClicked)
            2 -> PopularFilmsViewHolder.Fail(failedBinding, retry)
            3 -> PopularFilmsViewHolder.FilmNotFound(filmNotFoundBinding)
            else -> PopularFilmsViewHolder.Loading(loadingBinding)
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: PopularFilmsViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    override fun getItemViewType(position: Int): Int = when (currentList[position]) {
        is FilmUi.Favorite -> 0
        is FilmUi.Base -> 1
        is FilmUi.Failed -> 2
        is FilmUi.Failed.FilmNotFound -> 3
        is FilmUi.Progress -> 4
    }

    abstract class PopularFilmsViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {
        open fun bind(film: FilmUi) = Unit

        class Base(
            private val binding: FilmItemBinding,
            private val onItemClicked: ItemClick,
            private val onItemLongClicked: ItemLongClick
        ) : PopularFilmsViewHolder(binding.root) {
            override fun bind(film: FilmUi) {
                binding.root.setOnClickListener {
                    onItemClicked.onClick(film)
                }
                binding.root.setOnLongClickListener {
                    onItemLongClicked.onLongClick(film)
                    true
                }
                binding.tvFilmTitle.text = film.nameRu
                binding.tvGenreAndYearOfRelease.text = film.year
                binding.imStar.setImageResource(film.iconResId)
                Glide
                    .with(binding.imFilm)
                    .load(film.posterUrl)
                    .into(binding.imFilm)
            }
        }

        class Favorite(
            private val binding: FilmItemBinding,
            private val onItemClicked: ItemClick,
            private val onItemLongClicked: ItemLongClick
        ) :
            PopularFilmsViewHolder(binding.root) {
            override fun bind(film: FilmUi) {
                binding.root.setOnClickListener {
                    onItemClicked.onClick(film)
                }
                binding.root.setOnLongClickListener {
                    onItemLongClicked.onLongClick(film)
                    true
                }
                binding.tvFilmTitle.text = film.nameRu
                binding.tvGenreAndYearOfRelease.text = film.year
                binding.imStar.setImageResource(film.iconResId)
                Glide
                    .with(binding.imFilm)
                    .load(film.posterUrl)
                    .into(binding.imFilm)
            }
        }

        class Fail(
            private val binding: PopularFilmsErrorBinding,
            private val retry: Retry,
        ) : PopularFilmsViewHolder(binding.root) {
            override fun bind(film: FilmUi) {
                binding.tvExceptionMessage.text = film.getMessage()
                //getString(R.string.check_your_connection, exception) //todo
                binding.btRetry.setOnClickListener {
                    retry.retry()
                }
            }
        }
        class FilmNotFound(
            private val binding: FilmItemNotFoundBinding,
        ) : PopularFilmsViewHolder(binding.root){
        }

        class Loading(
            private val binding: PopularFilmsLoadingBinding,
        ) : PopularFilmsViewHolder(binding.root)
    }
}