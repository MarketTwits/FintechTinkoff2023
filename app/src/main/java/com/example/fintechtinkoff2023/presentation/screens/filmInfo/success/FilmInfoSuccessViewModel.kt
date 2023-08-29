package com.example.fintechtinkoff2023.presentation.screens.filmInfo.success

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.fintechtinkoff2023.core.communication.Communication
import com.example.fintechtinkoff2023.presentation.models.FilmInfoUi
import com.example.fintechtinkoff2023.presentation.screens.filmInfo.FilmInfoCommunication

class FilmInfoSuccessViewModel(
    private val communication: FilmInfoCommunication,
) : ViewModel(), Communication.Observe<FilmInfoUi> {
    override fun observe(owner: LifecycleOwner, observer: Observer<FilmInfoUi>) {
        communication.observe(owner, observer)
    }
}

interface FilmInfoSuccessCommunication : Communication.Mutable<FilmInfoUi.Base> {
    class Base : Communication.Abstract<FilmInfoUi.Base>(), FilmInfoSuccessCommunication
}