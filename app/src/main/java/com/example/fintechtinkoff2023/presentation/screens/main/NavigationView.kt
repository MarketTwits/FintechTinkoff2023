package com.example.fintechtinkoff2023.presentation.screens.main

import android.app.Activity
import android.content.Context
import android.os.Parcelable
import android.util.AttributeSet
import android.util.Log
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.getSystemService
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.findFragment
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import androidx.lifecycle.lifecycleScope
import com.example.fintechtinkoff2023.R
import com.example.fintechtinkoff2023.core.view.BaseCustomView
import com.example.fintechtinkoff2023.core.view.Screen
import com.example.fintechtinkoff2023.core.wrappers.Logger
import com.example.fintechtinkoff2023.databinding.MenuLayoutBinding
import com.example.fintechtinkoff2023.presentation.screens.favorites.FavoriteScreen
import com.example.fintechtinkoff2023.presentation.screens.popular.PopularScreen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

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
            binding.btPopularity.changeState(it.javaClass.simpleName)
            binding.btFavorites.changeState(it.javaClass.simpleName)
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