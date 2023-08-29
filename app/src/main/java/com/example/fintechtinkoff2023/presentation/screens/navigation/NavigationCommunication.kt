package com.example.fintechtinkoff2023.presentation.screens.navigation

import com.example.fintechtinkoff2023.core.communication.Communication
import com.example.fintechtinkoff2023.core.view.Screen

interface NavigationCommunication : Communication.Mutable<Screen> {
    class Base : Communication.Abstract<Screen>(), NavigationCommunication
}