package com.testapp.data.wrappers

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


//Generic response
class JsResponseWrapper<T> {
    val cars: T? = null
    val status: JsStatus = JsStatus()

    //response status
    inner class JsStatus {
        var code: Int = 0
        var message: String? = null
    }
}

//decode response
suspend fun <T> unwrapResponse(response: JsResponseWrapper<T>): T {
    val body = response.cars
    if (response.status.code == 200 && body != null) {
        return body
    } else {
        throw JsException(message = response.status.message, statusCode = response.status.code)
    }
}

//generic function to decode jason from desired class type
inline fun <reified T> Gson.fromJson(json: String): T =
    fromJson(json, object : TypeToken<T>() {}.type)



