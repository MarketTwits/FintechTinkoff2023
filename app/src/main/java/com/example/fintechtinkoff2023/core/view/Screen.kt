package com.example.fintechtinkoff2023.core.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.replace

interface Screen {
    fun show(fragmentManager: FragmentManager, containerId: Int, addToBackStack : Boolean)
    abstract class Add(
        private val fragmentClass: Class<out Fragment>
    ) : Screen {
        override fun show(
            fragmentManager: FragmentManager,
            containerId: Int,
            addToBackStack: Boolean
        ) {
            if (addToBackStack){
                fragmentManager.beginTransaction()
                    .add(containerId, fragmentClass.newInstance())
                    .addToBackStack(fragmentClass.name)
                    .commit()
            }else{
                fragmentManager.beginTransaction()
                    .add(containerId, fragmentClass.newInstance())
                    .commit()
            }

        }
    }
    abstract class Replace(
        private val fragmentClass: Class<out Fragment>
    ) : Screen {
        override fun show(
            fragmentManager: FragmentManager,
            containerId: Int,
            addToBackStack: Boolean
        ) {
            if (addToBackStack){
                fragmentManager.beginTransaction()
                    .replace(containerId, fragmentClass.newInstance())
                    .addToBackStack(fragmentClass.name)
                    .commit()
            }else{
                fragmentManager.beginTransaction()
                    .replace(containerId, fragmentClass.newInstance())
                    .commit()
            }
        }
    }
}