package com.example.fintechtinkoff2023.presentation.search


import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fintechtinkoff2023.core.communication.Communication
import com.example.fintechtinkoff2023.domain.model.FilmUi

class SearchFilmsViewModel(
    private val searchFilmsLoader: SearchFilmsLoader,
    private val communication: SearchFilmCommunication,
    private val communicationString: CheckStringCommunication,
) : ViewModel(), Communication.Observe<List<FilmUi>> {

    fun listenEditText(text: String) {
        if (text != communicationString.fetch()) {
            communicationString.map(text)
            searchFilmsLoader.load(viewModelScope, text)
        }
    }
    fun itemToCache(item: FilmUi) {
        searchFilmsLoader.itemToCache(viewModelScope, item)
    }

    override fun observe(owner: LifecycleOwner, observer: Observer<List<FilmUi>>) {
        communication.observe(owner, observer)
    }
}
