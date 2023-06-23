package com.testapp.domain.models

import com.testapp.R
import com.testapp.utils.ApplicationContextSingleton

data class AppOperationState(
    val error: UniversalError? = null,
    val status: DataStatus = DataStatus.CREATED
) {
    enum class DataStatus {
        CREATED, SUCCESS, ERROR, LOADING, COMPLETE
    }
}

fun unknownError() =
    AppOperationState(
        UniversalError(
            ApplicationContextSingleton.getString(
                R.string.unknown_error
            )
        )
    )

fun universalError(error: UniversalError) =
    AppOperationState(error, AppOperationState.DataStatus.ERROR)