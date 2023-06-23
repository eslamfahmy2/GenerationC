package com.testapp.utils

import com.testapp.R
import com.testapp.domain.models.AppOperationState
import com.testapp.domain.models.UniversalError
import com.testapp.domain.models.universalError
import com.testapp.domain.models.unknownError
import com.testapp.testing.EspressoIdlingResource
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import retrofit2.HttpException
import java.io.FileNotFoundException
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

/*
    Extension function used to wrap network calls in order to provide universal error handling.
    This should be used for all network calls.
 */
fun CoroutineScope.safeLaunchWithFlow(
    sharedFlow: MutableStateFlow<AppOperationState>,
    launchBody: suspend () -> Unit,
): Job {

    val scope = this
    val coroutineExceptionHandler = CoroutineExceptionHandler { coroutineContext, exception ->
        // handle thrown exceptions from coroutine scope
        when (exception) {
            //If an exception or error occurs during coroutine execution it will come here.
            // Note that all code in the coroutine after the error instance will not execute!
            /* is NoConnectivityException ->
                 scope.launch {
                     sharedFlow.emit(universalError(UniversalError(exception.localizedMessage)))
                 }*/

            is ConnectException ->
                scope.launch {
                    sharedFlow.emit(universalError(UniversalError(exception.localizedMessage)))
                }

            is SocketTimeoutException ->
                scope.launch {
                    sharedFlow.emit(universalError(UniversalError(exception.localizedMessage)))
                }

            is IOException -> {
                when (exception) {
                    is UnknownHostException -> {
                        scope.launch {
                            sharedFlow.emit(
                                universalError(
                                    UniversalError(
                                        getBadInternetErrorMessage(
                                            exception.localizedMessage
                                        )
                                    )
                                )
                            )
                        }
                    }
                    is FileNotFoundException -> {
                        scope.launch {
                            sharedFlow.emit(
                                universalError(
                                    UniversalError(
                                        getFileNotFoundMessage(
                                            exception.localizedMessage
                                        )
                                    )
                                )
                            )
                        }
                    }
                    else -> {
                        scope.launch {
                            sharedFlow.emit(universalError(UniversalError(exception.localizedMessage)))
                        }
                    }
                }
            }

            is HttpException ->
                scope.launch {
                    sharedFlow.emit(universalError(UniversalError(exception.localizedMessage)))
                }

            else -> if (!exception.localizedMessage.isNullOrEmpty()) {
                scope.launch {
                    sharedFlow.emit(universalError(UniversalError(exception.localizedMessage)))
                }
            } else {
                scope.launch {
                    sharedFlow.tryEmit(unknownError())
                }
            }
        }
    }

    return this.launchIdling(coroutineExceptionHandler) {
        sharedFlow.emit(AppOperationState(error = null, AppOperationState.DataStatus.LOADING))
        launchBody.invoke()
        sharedFlow.emit(AppOperationState(error = null, AppOperationState.DataStatus.COMPLETE))
    }
}

fun getFileNotFoundMessage(message: String?): String {
    val bottomMessage =
        ApplicationContextSingleton.getString(R.string.file_not_found)
    return "$bottomMessage $message"
}

fun getBadInternetErrorMessage(message: String?): String {
    val bottomMessage =
        ApplicationContextSingleton.getString(R.string.no_internet_connection_error_coroutine)
    return message ?: ("" + "\n\n" + bottomMessage)
}

/*
    Extension function used to attach idling resource listener for testing purposes
 */
fun CoroutineScope.launchIdling(
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> Unit
): Job {
    EspressoIdlingResource.increment()
    val job = this.launch(context, start, block)
    job.invokeOnCompletion { EspressoIdlingResource.decrement() }
    return job
}