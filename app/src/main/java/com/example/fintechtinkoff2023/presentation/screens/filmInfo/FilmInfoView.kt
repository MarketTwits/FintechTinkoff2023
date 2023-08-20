package com.example.fintechtinkoff2023.presentation.screens.filmInfo

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.example.fintechtinkoff2023.R
import com.example.fintechtinkoff2023.core.view.BaseCustomView
import com.example.fintechtinkoff2023.databinding.FilmInfoViewBinding
import com.example.fintechtinkoff2023.databinding.PopularFilmsErrorBinding
import com.example.fintechtinkoff2023.databinding.PopularFilmsLoadingBinding
import com.example.fintechtinkoff2023.presentation.utils.formatBoldString
import com.example.fintechtinkoff2023.presentation.utils.images.InitShimmerDrawable


class FilmInfoView : BaseCustomView<FilmInfoViewModel>,
    HandleInfoUiState {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override val clazz = FilmInfoViewModel::class.java
    private val bindingSuccess = FilmInfoViewBinding.inflate(LayoutInflater.from(context))
    private val loadingBinding = PopularFilmsLoadingBinding.inflate(LayoutInflater.from(context))
    private val errorBinding = PopularFilmsErrorBinding.inflate(LayoutInflater.from(context))

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        observe()
    }

    private fun observe() {
        viewModel.observeFilm(findViewTreeLifecycleOwner()!!) {
            it.handle(this)
        }
        viewModel.observeFilmId(findViewTreeLifecycleOwner()!!) {
            viewModel.loadInfoAboutFilm(it)
        }
    }

    override fun handleSuccess(
        screen: com.example.fintechtinkoff2023.presentation.screens.filmInfo.FilmInfoUi
    ) {
        changeState(bindingSuccess)
        with(bindingSuccess) {
            tvFilmName.text = screen.name
            tvCountry.text = formatBoldString(
                context.getString(R.string.countries),
                screen.country.joinToString { it.country })
            tvGenres.text = formatBoldString(
                context.getString(R.string.genres),
                screen.genres.joinToString { it.genre })
            tvFilmDescription.text = screen.description
            Glide.with(context)
                .load(screen.posterUrl)
                .placeholder(InitShimmerDrawable.Base(context.getColor(R.color.light_blue)).map())
                .into(imFilmPoster)
        }
    }

    override fun handleError(text: String) {
        changeState(errorBinding)
        with(errorBinding) {
            tvExceptionMessage.text = text
            btRetry.setOnClickListener {
                // FIXME
                viewModel.observeFilmId(findViewTreeLifecycleOwner()!!) {
                    viewModel.loadInfoAboutFilm(it)
                }
            }
        }
    }

    override fun handleLoading() {
        changeState(loadingBinding)
    }

    override fun changeState(binding: ViewBinding) {
        removeAllViews()
        addView(binding.root)
    }
}
