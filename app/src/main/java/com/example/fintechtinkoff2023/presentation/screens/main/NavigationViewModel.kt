package com.example.fintechtinkoff2023.presentation.screens.main

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.fintechtinkoff2023.core.communication.Communication
import com.example.fintechtinkoff2023.core.view.Screen
import com.example.fintechtinkoff2023.presentation.screens.popular.PopularScreen

class NavigationViewModel(
    private val navigation : NavigationCommunication
) : ViewModel(), Communication.Observe<Screen>{
    init {
        navigation.map(PopularScreen)
    }
    override fun observe(owner: LifecycleOwner, observer: Observer<Screen>) {
        navigation.observe(owner, observer)
    }
    fun show(screen : Screen){
        if (screen !== navigation.fetch()){
            navigation.map(screen)
        }
    }
    fun changeState(screen: Screen, changeButtonState: ChangeButtonState){
       changeButtonState.changeState(screen.javaClass.simpleName)
    }
}
