package com.example.fintechtinkoff2023.presentation.search


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fintechtinkoff2023.domain.FilmInteract
import com.example.fintechtinkoff2023.domain.model.FilmUi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchFilmsViewModel(
    private val filmsInteractor: FilmInteract,
) : ViewModel() {
    private val _searchText: MutableLiveData<String> = MutableLiveData()

    private val _searchFilms: MutableLiveData<List<FilmUi>> = MutableLiveData()
    val searchFilms: LiveData<List<FilmUi>> = _searchFilms

    private fun load(keywords: String) {
        viewModelScope.launch(Dispatchers.IO) {
            filmsInteractor.testFetchSearchFilms(keywords)
                .collectLatest {
                    withContext(Dispatchers.Main) {
                        _searchFilms.value = it
                    }
                }
        }
    }

    fun listen(text: String) {
        if (text != _searchText.value) {
            _searchText.value = text
            load(text)
        }
    }

    fun itemToCache(item: FilmUi) {
        viewModelScope.launch(Dispatchers.IO) {
            filmsInteractor.addOrRemoveFilm(item)
        }
    }
}