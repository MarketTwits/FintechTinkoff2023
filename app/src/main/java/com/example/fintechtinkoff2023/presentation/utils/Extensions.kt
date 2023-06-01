package com.example.fintechtinkoff2023.presentation.utils

import android.graphics.Typeface
import android.os.CountDownTimer
import android.text.Editable
import android.text.Spannable
import android.text.SpannableString
import android.text.TextWatcher
import android.text.style.StyleSpan
import androidx.fragment.app.Fragment
import com.example.fintechtinkoff2023.R
import com.example.fintechtinkoff2023.presentation.utils.text_handler.AfterTextChangedListener
import com.google.android.material.textfield.TextInputEditText

fun Fragment.navigationReplaceFragment(
    fragment: Fragment,
    addToBackStack: Boolean = true,
) {
    if (addToBackStack) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView, fragment)
            .addToBackStack(null)
            .commit()
    } else {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView, fragment)
            .commit()
    }
}

fun Fragment.navigationAddFragment(
    fragment: Fragment,
    addToBackStack: Boolean = true,
) {
    if (addToBackStack) {
        parentFragmentManager.beginTransaction()
            .add(R.id.fragmentContainerView, fragment)
            .addToBackStack(null)
            .commit()
    } else {
        parentFragmentManager.beginTransaction()
            .add(R.id.fragmentContainerView, fragment)
            .commit()
    }
}


fun Fragment.formatBoldString(title: String, text: String): SpannableString {
    val str = SpannableString(title + text)
    str.setSpan(
        StyleSpan(Typeface.BOLD),
        0,
        title.length,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    return str
}

fun TextInputEditText.afterTextChangedDelayed(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : AfterTextChangedListener {
        var timer: CountDownTimer? = null
        override fun afterTextChanged(editable: Editable?) {
            timer?.cancel()
            timer = object : CountDownTimer(1000, 1000) {
                override fun onTick(millisUntilFinished: Long) {}
                override fun onFinish() {
                    afterTextChanged.invoke(editable.toString())
                }
            }.start()
        }
    })
}

class AfterTextChangedDelayed(val editText: TextInputEditText) {
    fun listener(afterTextChanged: (String) -> Unit): TextWatcher {
        val listener = object : AfterTextChangedListener {
            var timer: CountDownTimer? = null
            override fun afterTextChanged(editable: Editable?) {
                timer?.cancel()
                timer = object : CountDownTimer(1000, 1000) {
                    override fun onTick(millisUntilFinished: Long) {}
                    override fun onFinish() {
                        afterTextChanged.invoke(editable.toString())
                    }
                }.start()
            }
        }
        editText.addTextChangedListener(listener)
        return listener
    }
}
