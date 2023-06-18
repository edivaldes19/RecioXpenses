package com.edival.recioxpenses.domain.model

sealed class Resource<out T> {
    data class Success<out T>(val data: T) : Resource<T>()
    data class Error<out T>(val message: String? = null) : Resource<T>()
}