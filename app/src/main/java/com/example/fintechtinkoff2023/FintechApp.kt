package com.example.fintechtinkoff2023

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.example.fintechtinkoff2023.core.Core
import com.example.fintechtinkoff2023.core.sl.DependencyContainer
import com.example.fintechtinkoff2023.core.sl.ProvideViewModel
import com.example.fintechtinkoff2023.core.sl.ViewModelsFactory

class FintechApp : Application(), ProvideViewModel {
    override fun <T : ViewModel> viewModel(owner: ViewModelStoreOwner, className: Class<T>): T {
        return ViewModelProvider(owner, viewModelFactory)[className]
    }

    private lateinit var viewModelFactory: ViewModelsFactory

    override fun onCreate() {
        super.onCreate()

        viewModelFactory = ViewModelsFactory(
            DependencyContainer.Base(Core(this))
        )
    }
}

