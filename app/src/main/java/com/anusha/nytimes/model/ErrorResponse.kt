package com.anusha.nytimes.model

data class ErrorResponse(
        val error: Error? = null,
        val code: String? = null,
        val message: String?
)