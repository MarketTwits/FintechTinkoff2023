package com.example.fintechtinkoff2023.presentation.screens.filmInfo.error

import com.example.fintechtinkoff2023.core.communication.Communication

interface RetryCommunication : Communication.Mutable<Unit> {
    class Base : Communication.Abstract<Unit>(), RetryCommunication
}