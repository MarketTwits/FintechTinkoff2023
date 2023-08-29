package com.example.fintechtinkoff2023.presentation.screens.filmInfo.error

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.fintechtinkoff2023.core.communication.Communication
import com.example.fintechtinkoff2023.presentation.models.FilmInfoUi
import com.example.fintechtinkoff2023.presentation.screens.filmInfo.FilmInfoCommunication

class FilmInfoErrorViewModel(
    private val communication : FilmInfoCommunication,
    private val retryCommunication: RetryCommunication
) : ViewModel(), Communication.Observe<FilmInfoUi> {
    override fun observe(owner: LifecycleOwner, observer: Observer<FilmInfoUi>) {
        communication.observe(owner, observer)
    }
    fun retry(){
        retryCommunication.map(Unit)
    }
}
