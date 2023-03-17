package com.example.fintechtinkoff2023.presentation.utils

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