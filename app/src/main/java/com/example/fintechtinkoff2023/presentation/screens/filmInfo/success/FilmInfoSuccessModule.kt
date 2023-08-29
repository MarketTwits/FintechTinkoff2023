package com.example.fintechtinkoff2023.presentation.screens.filmInfo.success

import com.example.fintechtinkoff2023.core.Core
import com.example.fintechtinkoff2023.core.sl.Module

class FilmInfoSuccessModule(private val core : Core) : Module<FilmInfoSuccessViewModel> {
    override fun viewModel(): FilmInfoSuccessViewModel =
         FilmInfoSuccessViewModel(core.filmInfoCommunication())

}