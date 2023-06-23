package com.testapp.domain.models

data class UniversalError(
    val message: String? = null,
    val code: Int? = null,
    val type: String? = null
)