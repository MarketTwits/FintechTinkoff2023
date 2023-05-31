package com.example.fintechtinkoff2023.core.communication

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

interface Communication<T : Any> {
    interface Update<T : Any>{
        fun map(source : T)
    }
    interface Observe<T : Any>{
        fun observe(owner : LifecycleOwner, observer: Observer<T>) = Unit
    }
    interface Mutable<T : Any> : Update<T>, Observe<T>
    abstract class Abstract<T : Any>(private val liveData: MutableLiveData<T>
                                     = MutableLiveData()
    ) : Mutable<T> {
        override fun map(source: T) {
            liveData.value = source
        }
        override fun observe(owner: LifecycleOwner, observer: Observer<T>) {
            liveData.observe(owner, observer)
        }
    }
}
