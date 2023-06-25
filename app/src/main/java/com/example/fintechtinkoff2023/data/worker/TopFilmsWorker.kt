package com.example.fintechtinkoff2023.data.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.fintechtinkoff2023.FintechApp
import com.example.fintechtinkoff2023.core.wrappers.Logger
import com.example.fintechtinkoff2023.domain.FilmInteract
import com.example.fintechtinkoff2023.presentation.screens.base.module.BaseModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


class TopFilmsWorker(
    context: Context,
    workerParameters: WorkerParameters,
) :
    CoroutineWorker(context, workerParameters) {
    override suspend fun doWork(): Result {
        return try {
            val interact = (applicationContext as FintechApp).providePeriodicInteractor()
            interact.updateTipFilms()
            Logger.Base().logError(message = "update")
            Result.success()
        } catch (e: Exception) {
            Logger.Base().logError(message = "failed")
            Result.failure()
        }
    }
}

interface ProvidePeriodicInteractor {
    fun providePeriodicInteractor(): FilmInteract
}
