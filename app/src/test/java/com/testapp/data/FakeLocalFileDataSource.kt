package com.testapp.data

import com.testapp.data.source.LocalFileDataSource
import com.testapp.data.wrappers.JsResponseWrapper
import com.testapp.data.wrappers.JsStatus
import com.testapp.domain.models.Car

class FakeLocalFileDataSource : LocalFileDataSource {

    private val carOne = Car(
        model = "2021",
        plate_number = "ABC 123",
        brand = "Honda",
        unit_price = 20.0,
        currency = "EGP",
        color = "RED"
    )
    private val carTwo = Car(
        model = "2022",
        plate_number = "ABC 123",
        brand = "Opel",
        unit_price = 70.5,
        currency = "EGP",
        color = "WHITE"
    )
    private val carsList = listOf(carOne, carTwo)

    override suspend fun readFileToJson(): JsResponseWrapper<List<Car>> {
        val wrap: JsResponseWrapper<List<Car>> = JsResponseWrapper()
        val status = JsStatus()
        status.code = 200
        status.message = "success"
        wrap.cars = carsList
        wrap.status = status
        return wrap
    }

}
