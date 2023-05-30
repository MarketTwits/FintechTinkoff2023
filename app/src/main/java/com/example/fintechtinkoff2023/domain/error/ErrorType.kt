package com.example.fintechtinkoff2023.domain.error

enum class ErrorType {
    NO_CONNECTION,
    SERVICE_UNAVAILABLE,
    GENERIC_ERROR
}
interface ErrorTypes{
    fun map()
}