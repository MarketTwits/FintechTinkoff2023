package com.example.fintechtinkoff2023.core

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

interface Screen{
    fun show(fragmentManager : FragmentManager, containerId : Int)

    abstract class Add(private val className : Class<out Fragment>) : Screen {
        override fun show(fragmentManager: FragmentManager, containerId: Int) {
            fragmentManager.beginTransaction()
                .add(containerId, className.newInstance())
                .addToBackStack(className.simpleName)
                .commit()
        }
    }
    abstract class Replace(private val className: Class<out Fragment>) : Screen {
        override fun show(fragmentManager: FragmentManager, containerId: Int) {
            fragmentManager.beginTransaction()
                .replace(containerId, className.newInstance())
                .commit()
        }
    }
}