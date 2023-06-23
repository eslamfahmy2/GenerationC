package com.testapp.data

import com.testapp.data.source.LocalFile
import com.testapp.data.wrappers.NoMatchingExceptionException
import com.testapp.data.wrappers.unwrapResponse
import com.testapp.domain.models.Car
import com.testapp.domain.repository.CarRepository

class CarRepositoryImpl(
    private val localDataSource: LocalFile
) : CarRepository {

    override suspend fun doSearchByColorAndPrice(color: String, price: String): List<Car> {
        //read file
        val carsList = unwrapResponse(localDataSource.getJsonFromFile())
        //filter conditions
        if (color.isEmpty() && price.isEmpty())
            return carsList
        val filteredCarsList = carsList.filter {
            it.color.lowercase().contains(color.lowercase())
        }
        if (price.isNotEmpty()) {
            return filteredCarsList.filter {
                it.unit_price <= price.toDouble()
            }
        }
        if (filteredCarsList.isEmpty()) {
            throw NoMatchingExceptionException
        }
        return filteredCarsList
    }
}
