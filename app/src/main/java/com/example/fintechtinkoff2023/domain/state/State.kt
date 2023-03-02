package com.example.fintechtinkoff2023.domain.state

import com.example.fintechtinkoff2023.data.network.model.PageFilm

sealed class State {
    object Initial : State()
    object Loading : State()
    object Error : State()
    data class Content(val data: PageFilm) : State()
}