package com.testapp.presentation.ui

import com.testapp.data.wrappers.NoMatchingExceptionException
import com.testapp.domain.models.Car
import com.testapp.domain.repository.CarRepository

class FakeCarRepository : CarRepository {

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

    override suspend fun doSearchByColorAndPrice(color: String, price: String): List<Car> {
        //filter conditions
        if (color.isEmpty() && price.isEmpty()) {
            carsList.ifEmpty { throw NoMatchingExceptionException }
            return carsList
        }
        val filteredByPrice: MutableList<Car> = carsList.toMutableList()
        if (price.isNotEmpty()) {
            filteredByPrice.clear()
            filteredByPrice.addAll(
                carsList.filter {
                    it.unit_price <= price.toDouble()
                }
            )
        }
        val filteredByColor = filteredByPrice.filter {
            it.color.lowercase().contains(color.lowercase())
        }
        if (filteredByColor.isEmpty()) {
            throw NoMatchingExceptionException
        }
        return filteredByColor
    }

    fun getCar() = carOne
}