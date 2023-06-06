package com.example.fintechtinkoff2023.presentation.filmInfo

import android.content.Context
import android.renderscript.ScriptGroup.Binding
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.ScrollView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.GravityCompat
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.example.fintechtinkoff2023.R
import com.example.fintechtinkoff2023.core.sl.ProvideViewModel
import com.example.fintechtinkoff2023.core.wrappers.ManageResource
import com.example.fintechtinkoff2023.databinding.FilmInfoViewBinding
import com.example.fintechtinkoff2023.databinding.PopularFilmsErrorBinding
import com.example.fintechtinkoff2023.databinding.PopularFilmsLoadingBinding
import com.example.fintechtinkoff2023.domain.model.FilmInfoUi
import com.example.fintechtinkoff2023.presentation.utils.formatBoldString
import kotlinx.coroutines.launch

class FilmInfoView : FrameLayout {
    val binding = FilmInfoViewBinding.inflate(LayoutInflater.from(context))
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    init {
        val binding = FilmInfoViewBinding.inflate(LayoutInflater.from(context))
        addView(binding.root)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        val viewModel : FilmInfoViewModel = (context.applicationContext as ProvideViewModel)
            .viewModel(
                findViewTreeViewModelStoreOwner()!!,
                FilmInfoViewModel::class.java
            )
        viewModel.observeFilm(findViewTreeLifecycleOwner()!!) {
            observerTopFilmLiveDataFlow(it)
        }
//        viewModel.observeFilmId(findViewTreeLifecycleOwner()!!){
//
//        }
    }

    private fun observerTopFilmLiveDataFlow(filmInfo: FilmInfoUi) {
        removeAllViews()
        when (filmInfo) {
            is FilmInfoUi.Base -> {
                val binding = FilmInfoViewBinding.inflate(LayoutInflater.from(context))
                addView(binding.root)
                setUpUI(filmInfo, binding)
            }
            is FilmInfoUi.Progress -> {
                val loadingBinding = PopularFilmsLoadingBinding.inflate(LayoutInflater.from(context))
                addView(loadingBinding.root)
            }
            is FilmInfoUi.Failed -> {
                val errorBinding = PopularFilmsErrorBinding.inflate(LayoutInflater.from(context))
                addView(errorBinding.root)
            }
            else ->  {
                val errorBinding = PopularFilmsErrorBinding.inflate(LayoutInflater.from(context))
                addView(errorBinding.root)
                errorBinding.tvExceptionMessage.text = filmInfo.getMessage()
            }
        }
    }
    private fun setUpUI(film: FilmInfoUi, binding: FilmInfoViewBinding) {
        val manageResource = ManageResource.Base(context)
        binding.tvFilmName.text = film.name
        binding.tvCountry.text =
            formatBoldString(manageResource.string(R.string.countries), film.country.joinToString { it.country })
        binding.tvGenres.text =
            formatBoldString(manageResource.string(R.string.genres), film.genres.joinToString { it.genre })
        binding.tvFilmDescription.text = film.description
        Glide.with(context).load(film.posterUrl).into(binding.imFilmPoster)
    }
    private fun handleError(film: FilmInfoUi, binding: PopularFilmsErrorBinding){
        binding.tvExceptionMessage.text = film.getMessage()
        binding.btRetry.setOnClickListener {

        }
    }
}