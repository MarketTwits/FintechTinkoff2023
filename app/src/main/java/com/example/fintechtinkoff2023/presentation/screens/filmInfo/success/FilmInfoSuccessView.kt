package com.example.fintechtinkoff2023.presentation.screens.filmInfo.success

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import com.bumptech.glide.Glide
import com.example.fintechtinkoff2023.R
import com.example.fintechtinkoff2023.core.view.BaseCustomView
import com.example.fintechtinkoff2023.databinding.FilmInfoViewBinding
import com.example.fintechtinkoff2023.presentation.screens.filmInfo.FilmInfoUi
import com.example.fintechtinkoff2023.presentation.screens.filmInfo.HandleInfoUiState
import com.example.fintechtinkoff2023.presentation.utils.formatBoldString
import com.example.fintechtinkoff2023.presentation.utils.images.InitShimmerDrawable

class FilmInfoSuccessView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : BaseCustomView<FilmInfoSuccessViewModel>(context, attrs), HandleInfoUiState {

    override val clazz = FilmInfoSuccessViewModel::class.java
    private lateinit var binding : FilmInfoViewBinding
    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        binding = FilmInfoViewBinding.inflate(LayoutInflater.from(context), this, false)
        viewModel =
            viewModel(findViewTreeViewModelStoreOwner()!!, FilmInfoSuccessViewModel::class.java)

        viewModel.observe(findViewTreeLifecycleOwner()!!){
            if (it is com.example.fintechtinkoff2023.presentation.models.FilmInfoUi.Base){
                addView(binding.root)
                it.handle(this)
            }else{
                removeAllViews()
            }
        }

    }

    override fun handleSuccess(screen: FilmInfoUi) {
        with(binding) {
            tvFilmName.text = screen.name
            tvCountry.text = formatBoldString(
                context.getString(R.string.countries),
                screen.country.joinToString { it.country })
            tvGenres.text = formatBoldString(
                context.getString(R.string.genres),
                screen.genres.joinToString { it.genre })
            tvFilmDescription.text = screen.description
            Glide.with(context).load(screen.posterUrl).placeholder(InitShimmerDrawable.Base(context.getColor(R.color.light_blue)).map()).into(imFilmPoster)
        }
    }
}