package com.example.fintechtinkoff2023.presentation.screens.navigation

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import androidx.lifecycle.findViewTreeLifecycleOwner
import com.example.fintechtinkoff2023.R
import com.example.fintechtinkoff2023.core.view.BaseCustomView
import com.example.fintechtinkoff2023.databinding.MenuLayoutBinding
import com.example.fintechtinkoff2023.presentation.screens.favorites.FavoriteScreen
import com.example.fintechtinkoff2023.presentation.screens.main.ChangeButtonState
import com.example.fintechtinkoff2023.presentation.screens.popular.PopularScreen

class NavigationView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : BaseCustomView<NavigationViewModel>(context, attrs) {

    override val clazz = NavigationViewModel::class.java
    private lateinit var binding: MenuLayoutBinding

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        binding = MenuLayoutBinding.inflate(LayoutInflater.from(context), this, true)

        viewModel.observe(findViewTreeLifecycleOwner()!!) {
            it.show(getFragmentManager(context)!!, R.id.fragmentContainerView, false)
            //TODO fix it
            viewModel.changeState(it, binding.btFavorites)
            viewModel.changeState(it, binding.btPopularity)

        }
        setupListeners()
    }
    private fun setupListeners() {
        binding.btFavorites.setOnClickListener {
            viewModel.show(FavoriteScreen)
        }
        binding.btPopularity.setOnClickListener {
            viewModel.show(PopularScreen)
        }
    }
}