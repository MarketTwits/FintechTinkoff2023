package com.example.fintechtinkoff2023.presentation.utils.adapterListener

import com.example.fintechtinkoff2023.presentation.models.FilmUi


interface ItemActions{
    interface ItemClick {
        fun onClick(filmUi: FilmUi)
    }
    interface ItemLongClick{
        fun onLongClick(filmUi: FilmUi)
    }
    interface Retry{
        fun retry()
    }
    interface Mutable : ItemClick, ItemLongClick, Retry
    interface MutableWithoutRetry : ItemClick, ItemLongClick
}