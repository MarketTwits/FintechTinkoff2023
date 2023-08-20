package com.example.fintechtinkoff2023.presentation.screens.main

import com.example.fintechtinkoff2023.core.sl.Module

class NavigationModule : Module<NavigationViewModel> {
    override fun viewModel() = NavigationViewModel(NavigationCommunication.Base())
}