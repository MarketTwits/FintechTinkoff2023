package com.example.fintechtinkoff2023.core

import androidx.lifecycle.ViewModel

interface Module<T : ViewModel>{
   fun viewModel() : T

}