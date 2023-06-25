package com.example.fintechtinkoff2023.core.wrappers

import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.fintechtinkoff2023.data.worker.TopFilmsWorker
import java.util.concurrent.TimeUnit

interface WorkManagerWrapper {
    fun start()
    class Base(context: Context) : WorkManagerWrapper {
        private val workManager = WorkManager.getInstance(context)
        override fun start() {
            val request = PeriodicWorkRequestBuilder<TopFilmsWorker>(
                15, TimeUnit.MINUTES
            )
                .setConstraints(
                    Constraints.Builder()
                        .setRequiredNetworkType(NetworkType.CONNECTED)
                        .build()
                )
                .build()
            workManager.enqueueUniquePeriodicWork(
                WORK_NAME,
                ExistingPeriodicWorkPolicy.KEEP,
                request
            )
        }
        companion object {
            private const val WORK_NAME = "number work"
        }
    }
}