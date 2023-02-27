package com.example.fintechtinkoff2023.presentation.popular

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.fintechtinkoff2023.R
import com.example.fintechtinkoff2023.data.network.KinoPoiskApi
import com.example.fintechtinkoff2023.data.network.RetrofitInstance
import com.example.fintechtinkoff2023.data.network.model.Film
import com.example.fintechtinkoff2023.databinding.FragmentPopularBinding
import com.example.fintechtinkoff2023.presentation.popular.adapter.TopFilmsAdapter
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class PopularFragment : Fragment() {

    lateinit var binding: FragmentPopularBinding
    val viewModel by viewModels<PopularFilmsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentPopularBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //getTopFilms()
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        val adapter = TopFilmsAdapter()
        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvTopFilms.adapter = adapter
        binding.rvTopFilms.layoutManager = layoutManager
        lifecycleScope.launch {
            val films = getTopFilms()
            adapter.submitList(films)
        }
    }

    suspend fun getTopFilms(): List<Film> {
        val retrofitInstance = RetrofitInstance.kinoPoiskApiInstance
        val result = lifecycleScope.async {
            retrofitInstance.getTopFilms().films
        }
        return result.await()
    }




}

