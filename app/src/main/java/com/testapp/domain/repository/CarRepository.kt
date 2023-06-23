package com.testapp.domain.repository

import com.testapp.domain.models.Car


interface CarRepository {
    suspend fun doSearchByColorAndPrice(color: String, price: String): List<Car>
}





