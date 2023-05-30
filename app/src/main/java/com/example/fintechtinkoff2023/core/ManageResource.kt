package com.example.fintechtinkoff2023.core

import android.content.Context

interface ManageResource {
    fun string(id : Int) : String
    class Base(private val context: Context) : ManageResource{
        override fun string(id: Int) = context.getString(id)
    }
}