package com.example.fintechtinkoff2023.core.communication

import com.example.fintechtinkoff2023.core.wrappers.Screen


interface NavigationCommunication {
    interface Observe : Communication.Observe<Screen>
    interface Update : Communication.Update<Screen>
    interface Mutable : Observe, Update
    class Base : Communication.Abstract<Screen>(), Mutable
}
