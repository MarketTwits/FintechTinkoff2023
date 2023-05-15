package com.example.fintechtinkoff2023.presentation.utils.adapterListener

import com.example.fintechtinkoff2023.domain.model.FilmUi

interface ItemClick {
    fun onClick(filmUi: FilmUi)
}
interface ItemLongClick{
    fun onLongClick(filmUi: FilmUi)
}
interface Retry{
    fun retry()
}