package com.example.fintechtinkoff2023.presentation.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStoreOwner
import com.example.fintechtinkoff2023.R
import com.example.fintechtinkoff2023.core.sl.ProvideViewModel

class MainActivity : AppCompatActivity(), ProvideViewModel {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContentView(R.layout.activity_main)
    }
    override fun <T : ViewModel> viewModel(owner: ViewModelStoreOwner, className: Class<T>): T {
        return (application as ProvideViewModel).viewModel(owner, className)
    }
}