package com.example.fintechtinkoff2023.core.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStoreOwner
import androidx.viewbinding.ViewBinding
import com.example.fintechtinkoff2023.core.sl.ProvideViewModel

abstract class BaseFragment<VM : ViewModel, VB : ViewBinding>(
    private val inflate: (LayoutInflater, ViewGroup?, Boolean) -> VB
) : Fragment(), ProvideViewModel {

    protected lateinit var viewModel: VM
    protected lateinit var binding : VB
    protected abstract val clazz: Class<VM>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = inflate(inflater, container, false)
        return binding.root
    }

    override fun <V : ViewModel> viewModel(owner: ViewModelStoreOwner, className: Class<V>): V
    = (requireActivity().application as ProvideViewModel).viewModel(owner, className)

}
