package com.example.fintechtinkoff2023.presentation.screens.filmInfo.loading

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.lifecycle.findViewTreeLifecycleOwner
import com.example.fintechtinkoff2023.core.view.BaseCustomView
import com.example.fintechtinkoff2023.databinding.FilmLoadingBinding
import com.example.fintechtinkoff2023.presentation.models.FilmInfoUi
import com.example.fintechtinkoff2023.presentation.screens.filmInfo.HandleInfoUiState

class FilmInfoLoadingView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : BaseCustomView<FilmInfoLoadingViewModel>(context, attrs), HandleInfoUiState  {

    private lateinit var binding : FilmLoadingBinding
    override val clazz =  FilmInfoLoadingViewModel::class.java

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        binding = FilmLoadingBinding.inflate(LayoutInflater.from(context), this,false)
        viewModel.observe(findViewTreeLifecycleOwner()!!) {
            if (it is FilmInfoUi.Progress){
                it.handle(this)
            }else{
                removeAllViews()
            }
        }
    }

    override fun handleLoading() {
        removeAllViews()
        addView(binding.root)
    }
}
