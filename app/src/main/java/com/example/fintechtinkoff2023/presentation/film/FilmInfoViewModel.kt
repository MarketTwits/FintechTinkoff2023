package com.example.fintechtinkoff2023.presentation.film

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fintechtinkoff2023.domain.FilmInteract
import com.example.fintechtinkoff2023.domain.model.FilmInfoUi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FilmInfoViewModel
    (private val interactor: FilmInteract
) : ViewModel() {

    private val _infoFilmsCloud: MutableLiveData<FilmInfoUi> = MutableLiveData()
    val infoFilmsCloud: LiveData<FilmInfoUi> = _infoFilmsCloud


    fun loadInfoAboutFilm(filmId: Int) {
        viewModelScope.launch {
            viewModelScope.launch(Dispatchers.IO) {
                interactor.fetchInfoFilm(filmId)
                interactor.infoAboutFilm.collect {
                    withContext(Dispatchers.Main) {
                        _infoFilmsCloud.value = it
                    }
                }
            }
        }
    }
}