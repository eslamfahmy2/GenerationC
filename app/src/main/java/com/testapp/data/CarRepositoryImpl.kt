package com.testapp.data

import com.testapp.data.source.LocalFileDataSource
import com.testapp.data.wrappers.NoMatchingExceptionException
import com.testapp.data.wrappers.unwrapResponse
import com.testapp.di.IoDispatcher
import com.testapp.domain.models.Car
import com.testapp.domain.repository.CarRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext


class CarRepositoryImpl(
    private val localDataSource: LocalFileDataSource,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : CarRepository {

    override suspend fun doSearchByColorAndPrice(color: String, price: String): List<Car> =
        withContext(dispatcher) {
            //read file
            val carsList = unwrapResponse(localDataSource.readFileToJson())
            //filter conditions
            if (color.isEmpty() && price.isEmpty()) {
                carsList.ifEmpty { throw NoMatchingExceptionException }
                return@withContext carsList
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
            return@withContext filteredByColor
        }
}
