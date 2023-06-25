package com.example.fintechtinkoff2023.presentation.screens.main

import com.example.fintechtinkoff2023.core.Core
import com.example.fintechtinkoff2023.core.sl.Module

class MainModule(private val core : Core) : Module<MainViewModel> {
    override fun viewModel() = MainViewModel(core.provideWorkManagerWrapper())
}