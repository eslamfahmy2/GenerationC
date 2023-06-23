package com.testapp.data.source

import android.content.res.AssetManager
import com.google.gson.Gson
import com.testapp.data.wrappers.JsResponseWrapper
import com.testapp.data.wrappers.fromJson
import com.testapp.domain.models.Car


const val FILE_SUCCESS_NAME = "cars-success.json"
const val FILE_FAIL_NAME = "cars-fail.json"

class LocalFile constructor(
    private val assetManager: AssetManager
) {

    suspend fun getJsonFromFile(): JsResponseWrapper<List<Car>> {
        //read file
        val rawData = assetManager.open(FILE_SUCCESS_NAME)
            .bufferedReader()
            .use { it.readText() }
        //convert text to json
        return Gson().fromJson<JsResponseWrapper<List<Car>>>(rawData)
    }

}