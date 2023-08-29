package com.example.fintechtinkoff2023.core.view

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fintechtinkoff2023.databinding.FilmErrorBinding
import com.example.fintechtinkoff2023.databinding.FilmItemBinding
import com.example.fintechtinkoff2023.databinding.FilmItemNotFoundBinding
import com.example.fintechtinkoff2023.databinding.FilmLoadingBinding
import com.example.fintechtinkoff2023.databinding.PopularFilmsScreenBinding
import com.example.fintechtinkoff2023.presentation.models.FilmUi
import com.example.fintechtinkoff2023.presentation.utils.adapterListener.ItemActions


abstract class BaseFilmsViewHolder(view: View) :
    RecyclerView.ViewHolder(view) {
    open fun bind(film: FilmUi) = Unit

    class Base(
        private val binding: FilmItemBinding,
        private val action: ItemActions.Mutable
    ) : BaseFilmsViewHolder(binding.root) {
        override fun bind(film: FilmUi) {
            binding.root.setOnClickListener {
                action.onClick(film)
            }
            binding.root.setOnLongClickListener {
                action.onLongClick(film)
                true
            }
            binding.tvFilmTitle.text = film.nameRu
            binding.tvGenreAndYearOfRelease.text = film.year
            binding.imStar.setImageResource(film.iconResId)
            Glide.with(binding.imFilm)
                .load(film.posterUrl)
                .into(binding.imFilm)
        }
    }

    class Favorite(
        private val binding: FilmItemBinding,
        private val action : ItemActions.Mutable
    ) :
        BaseFilmsViewHolder(binding.root) {
        override fun bind(film: FilmUi) {
            binding.root.setOnClickListener {
                action.onClick(film)
            }
            binding.root.setOnLongClickListener {
                action.onLongClick(film)
                true
            }
            binding.tvFilmTitle.text = film.nameRu
            binding.tvGenreAndYearOfRelease.text = film.year
            binding.imStar.setImageResource(film.iconResId)
            Glide.with(binding.imFilm)
                .load(film.posterUrl)
                .into(binding.imFilm)
        }
    }

    class Fail(
        private val binding: FilmErrorBinding,
        private val retry: ItemActions.Mutable,
    ) : BaseFilmsViewHolder(binding.root) {
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
    ) : BaseFilmsViewHolder(binding.root){
    }

    class Loading(
        private val binding: FilmLoadingBinding,
    ) : BaseFilmsViewHolder(binding.root)
}