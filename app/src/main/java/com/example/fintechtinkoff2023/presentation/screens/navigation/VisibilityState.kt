package com.example.fintechtinkoff2023.presentation.screens.navigation

import android.os.Parcel
import android.os.Parcelable
import android.view.View
import com.example.fintechtinkoff2023.R

class VisibilityState : View.BaseSavedState {
    var visibility: Int = View.VISIBLE
    var textColor : Int = R.color.blue
    var backgroundColor : Int = R.color.white

    constructor(superState: Parcelable) : super(superState)
    private constructor(parcelIn: Parcel) : super(parcelIn) {
        visibility = parcelIn.readInt()
        textColor = parcelIn.readInt()
        backgroundColor = parcelIn.readInt()
    }

    override fun writeToParcel(out: Parcel, flags: Int) {
        super.writeToParcel(out, flags)
        out.writeInt(visibility)
        out.writeInt(textColor)
        out.writeInt(backgroundColor)
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<VisibilityState> {
        override fun createFromParcel(parcel: Parcel): VisibilityState = VisibilityState(parcel)
        override fun newArray(size: Int): Array<VisibilityState?> = arrayOfNulls(size)
    }
}