package com.example.fintechtinkoff2023.domain.state

import com.example.fintechtinkoff2023.domain.error.ErrorType

sealed class NetworkResult<T>(open val data: T, val errorType: ErrorType) {
    data class Success<T>(override val data: T) : NetworkResult<T>(data, ErrorType.UNIT)
    data class Error<T>(val errorMessage: ErrorType) : NetworkResult<T>(Unit as T, errorMessage) {
         class NotFound<T>() : NetworkResult<T>(errorType = ErrorType.NOT_FOUND, data = Unit as T)
    }
     class Loading<T> : NetworkResult<T>(Unit as T, ErrorType.UNIT)
}