package com.example.fintechtinkoff2023.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelsFactory(
    private val dependencyContainer: DependencyContainer
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        dependencyContainer.module(modelClass).viewModel() as T
}