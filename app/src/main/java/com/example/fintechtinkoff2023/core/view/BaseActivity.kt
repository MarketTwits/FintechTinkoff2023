package com.example.fintechtinkoff2023.core.view
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStoreOwner
import com.example.fintechtinkoff2023.core.sl.ProvideViewModel
abstract class BaseActivity<V : ViewModel> : AppCompatActivity(),
    ProvideViewModel {
    protected lateinit var viewModel: V
    abstract val layoutRes : Int
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutRes)
        initViewModel()
    }
    abstract fun initViewModel()

    override fun <V : ViewModel> viewModel(owner: ViewModelStoreOwner, className: Class<V>): V  =
        (application as ProvideViewModel).viewModel(owner, className)
}