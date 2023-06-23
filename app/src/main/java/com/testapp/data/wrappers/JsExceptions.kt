package com.testapp.data.wrappers

import com.testapp.R
import com.testapp.utils.ApplicationContextSingleton

object NoMatchingExceptionException : Exception() {
    override fun getLocalizedMessage(): String {
        return ApplicationContextSingleton.getString(R.string.no_matching_search)
    }
}

object NotNumberException : Exception() {
    override fun getLocalizedMessage(): String {
        return ApplicationContextSingleton.getString(R.string.input_not_valid)
    }
}

class JsException(
    override val message: String?,
    val statusCode: Int? = null,
) : Exception()
