package com.example.fintechtinkoff2023.presentation.screens.main

import androidx.lifecycle.ViewModel
import com.example.fintechtinkoff2023.core.wrappers.WorkManagerWrapper

class MainViewModel(
    private val workManagerWrapper: WorkManagerWrapper
) : ViewModel() {
    init {
        workManagerWrapper.start()
    }
}