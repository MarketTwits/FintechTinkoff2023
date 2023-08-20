package com.example.fintechtinkoff2023.core.view

import android.content.Context
import android.util.AttributeSet
import android.view.ContextThemeWrapper
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import com.example.fintechtinkoff2023.core.sl.ProvideViewModel

abstract class BaseCustomView<VM : ViewModel>
 : FrameLayout, ProvideViewModel {
    protected lateinit var viewModel: VM
    protected abstract val clazz: Class<VM>
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )
    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        viewModel = viewModel(findViewTreeViewModelStoreOwner()!!, clazz)
    }
    protected fun getFragmentManager(context: Context?): FragmentManager? {
        return when (context) {
            is AppCompatActivity -> context.supportFragmentManager
            is ContextThemeWrapper -> getFragmentManager(context.baseContext)
            else -> null
        }
    }

    override fun <T : ViewModel> viewModel(owner: ViewModelStoreOwner, className: Class<T>): T =
        (context.applicationContext as ProvideViewModel).viewModel(owner, className)
}