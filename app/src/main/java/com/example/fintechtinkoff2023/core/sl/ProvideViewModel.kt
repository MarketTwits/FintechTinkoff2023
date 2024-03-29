package com.example.fintechtinkoff2023.core.sl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStoreOwner

interface ProvideViewModel {
    fun <T : ViewModel> viewModel(owner: ViewModelStoreOwner, className: Class<T>): T
}