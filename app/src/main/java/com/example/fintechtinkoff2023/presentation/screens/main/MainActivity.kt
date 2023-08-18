package com.example.fintechtinkoff2023.presentation.screens.main

import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.fintechtinkoff2023.R
import com.example.fintechtinkoff2023.core.view.BaseActivity

class MainActivity: BaseActivity<MainViewModel>() {
    override val layoutRes = R.layout.activity_main
    override fun initViewModel() {
        viewModel = viewModel(this,MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
    }
}