package com.example.fintechtinkoff2023.presentation.screens.filmInfo.error

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.lifecycle.findViewTreeLifecycleOwner
import com.example.fintechtinkoff2023.core.sl.ProvideViewModel
import com.example.fintechtinkoff2023.core.view.BaseCustomView
import com.example.fintechtinkoff2023.databinding.FilmErrorBinding
import com.example.fintechtinkoff2023.presentation.models.FilmInfoUi
import com.example.fintechtinkoff2023.presentation.screens.filmInfo.HandleInfoUiState

class FilmInfoErrorView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : BaseCustomView<FilmInfoErrorViewModel>(context, attrs), ProvideViewModel, HandleInfoUiState {
    override val clazz: Class<FilmInfoErrorViewModel> = FilmInfoErrorViewModel::class.java
    lateinit var binding : FilmErrorBinding
    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        binding = FilmErrorBinding.inflate(LayoutInflater.from(context), this, false)

        viewModel.observe(findViewTreeLifecycleOwner()!!) {
            if (it is FilmInfoUi.Failed){
                it.handle(this)
            }else{
                removeAllViews()
            }
        }
    }

    override fun handleError(text: String) {
        removeAllViews()
        addView(binding.root)
        with(binding) {
            tvExceptionMessage.text = text
            btRetry.setOnClickListener {
                //todo
            }
        }
    }
}