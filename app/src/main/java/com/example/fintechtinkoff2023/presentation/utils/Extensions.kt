package com.example.fintechtinkoff2023.presentation.utils

import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import androidx.fragment.app.Fragment
import com.example.fintechtinkoff2023.R

fun Fragment.navigation(
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
