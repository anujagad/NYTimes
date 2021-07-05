package com.anusha.nytimes.utils

import com.anusha.nytimes.model.ApiError
import okhttp3.Headers

sealed class Result<out T : Any> {
    class Success<out T : Any>(val data: T) : Result<T>()
    class Error(val exception: ApiError) : Result<Nothing>()
    data class Complete(val headers: Headers) : Result<Nothing>()
}