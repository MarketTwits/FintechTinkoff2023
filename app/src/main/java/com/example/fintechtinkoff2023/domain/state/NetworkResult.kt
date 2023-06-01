package com.example.fintechtinkoff2023.domain.state

import com.example.fintechtinkoff2023.domain.error.ErrorType

sealed class NetworkResult<T>( val data: T,  val errorType: ErrorType) {

    class Success<T>(data: T) : NetworkResult<T>(data, ErrorType.UNIT)
    class Error<T>(errorMessage: ErrorType) : NetworkResult<T>(Unit as T, errorMessage) {
        class NotFound<T>() : NetworkResult<T>(errorType = ErrorType.UNIT, data = Unit as T)
    }

    class Loading<T> : NetworkResult<T>(Unit as T, ErrorType.UNIT)


}