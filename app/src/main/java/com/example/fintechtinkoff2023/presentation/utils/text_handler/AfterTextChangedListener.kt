package com.example.fintechtinkoff2023.presentation.utils.text_handler

import android.text.Editable
import android.text.TextWatcher

interface AfterTextChangedListener : TextWatcher {
    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit
    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit
    override fun afterTextChanged(p0: Editable?)
}