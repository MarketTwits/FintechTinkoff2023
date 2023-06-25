package com.example.fintechtinkoff2023.domain.error

import android.view.WindowManager.BadTokenException
import com.bumptech.glide.load.HttpException
import java.net.UnknownHostException
import kotlin.Exception

interface ErrorTypeDomainMapper {
    suspend fun map(exception: Exception): ErrorType
    class Base() : ErrorTypeDomainMapper {
        override suspend fun map(exception: Exception): ErrorType {

            val errorType = when (exception) {
                is UnknownHostException -> ErrorType.NO_CONNECTION
                is HttpException -> ErrorType.SERVICE_UNAVAILABLE
                else -> ErrorType.GENERIC_ERROR
            }
            return errorType
        }
    }
}