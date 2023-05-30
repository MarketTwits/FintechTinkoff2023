package com.example.fintechtinkoff2023.domain.state

sealed class NetworkResult<T>(val data: T, val message: String) {

    class Success<T>(data: T) : NetworkResult<T>(data, "")
    class Error<T>(errorMessage: String) : NetworkResult<T>(Unit as T, errorMessage) {
        class NotFound<T>() : NetworkResult<T>(message = "", data = Unit as T)
    }
    class Loading<T> : NetworkResult<T>(Unit as T, "")


}