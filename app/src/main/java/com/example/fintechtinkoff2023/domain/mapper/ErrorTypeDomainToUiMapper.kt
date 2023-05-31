package com.example.fintechtinkoff2023.domain.mapper

import com.example.fintechtinkoff2023.R
import com.example.fintechtinkoff2023.core.wrappers.ManageResource
import com.example.fintechtinkoff2023.domain.error.ErrorType

interface ErrorTypeDomainToUiMapper {
    suspend fun map(errorType: ErrorType): String
    class Base(private val manageResource: ManageResource) : ErrorTypeDomainToUiMapper {
        override suspend fun map(errorType: ErrorType): String {
            val message = when (errorType) {
                ErrorType.NO_CONNECTION -> manageResource.string(R.string.no_connection_message)
                ErrorType.SERVICE_UNAVAILABLE -> manageResource.string(R.string.service_unavalible)
                ErrorType.GENERIC_ERROR -> manageResource.string(R.string.generic_error)
                else -> {
                    manageResource.string(R.string.generic_error)
                }
            }
            return message

        }
    }
}