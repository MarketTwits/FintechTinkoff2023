package com.example.fintechtinkoff2023.presentation.screens.main

import android.annotation.SuppressLint
import android.content.Context
import android.os.Parcelable
import android.util.AttributeSet
import android.widget.Button
import com.example.fintechtinkoff2023.R

@SuppressLint("AppCompatCustomView")
class CustomButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : Button(context, attrs), ChangeButtonState {

    override fun onSaveInstanceState(): Parcelable? = super.onSaveInstanceState()?.let {
        val visibilityState = VisibilityState(it)
        visibilityState.visibility = visibility
        return visibilityState
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        val progressState = state as VisibilityState?
        super.onRestoreInstanceState(progressState)
        progressState?.let {
            visibility = it.visibility
        }
    }
    override fun changeState(tagz: String) {
        if (tag == tagz){
            setBackgroundResource(R.drawable.background_corner_15_pressed)
            setTextColor(resources.getColor(R.color.white))
        }else{
            setBackgroundResource(R.drawable.background_corner_15_base)
            setTextColor(resources.getColor(R.color.blue))
        }
    }
}
interface ChangeButtonState{
    fun changeState(tagz : String = "empty")
}