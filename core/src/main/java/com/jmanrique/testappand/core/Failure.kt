package com.jmanrique.testappand.core

import retrofit2.HttpException
import java.io.IOException

sealed class Failure {
    data class ApiError(val message: String?, val code: Int? = null, val throwable: Throwable? = null) : Failure()
    data class GenericError(val message: String?, val throwable: Throwable? = null) : Failure()
    data class DatabaseError(val message: String?, val throwable: Throwable? = null) : Failure()
    object NetworkConnection : Failure()

    fun getErrorMessage(): String {
        return when (this) {
            is ApiError -> message ?: "API Error"
            is GenericError -> message ?: "Unknown Error"
            is DatabaseError -> message ?: "Database Error"
            is NetworkConnection -> "No network connection"
        }
    }

    fun getAssociatedThrowable(): Throwable? {
        return when (this) {
            is ApiError -> throwable
            is GenericError -> throwable
            is DatabaseError -> throwable
            else -> null
        }
    }

    companion object {
        fun analyzeException(e: Throwable): Failure {
            return when (e) {
                is HttpException -> ApiError(message = e.message(), code = e.code(), throwable = e)
                is IOException -> NetworkConnection
                else -> GenericError(message = e.message, throwable = e)
            }
        }
    }
}
