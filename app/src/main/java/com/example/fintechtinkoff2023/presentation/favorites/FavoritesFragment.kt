package com.example.fintechtinkoff2023.presentation.favorites

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.fintechtinkoff2023.R
import com.example.fintechtinkoff2023.databinding.FragmentFavoritesBinding
import com.example.fintechtinkoff2023.presentation.popular.PopularFragment
import com.example.fintechtinkoff2023.presentation.utils.navigation


class FavoritesFragment : Fragment() {
    lateinit var binding : FragmentFavoritesBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View{
        // Inflate the layout for this fragment
        binding = FragmentFavoritesBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpListeners()
    }
    private fun setUpListeners(){
        binding.btPopularity.setOnClickListener {
            navigation(PopularFragment(), false)
        }
    }
}