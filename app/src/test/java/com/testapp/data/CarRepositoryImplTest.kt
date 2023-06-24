package com.testapp.data

import com.testapp.data.source.LocalFileDataSource
import com.testapp.data.wrappers.NoMatchingExceptionException
import com.testapp.domain.repository.CarRepository
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class CarRepositoryImplTest {


    private lateinit var carRepository: CarRepository
    lateinit var localFileDataSource: LocalFileDataSource

    //testing dispatcher
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        localFileDataSource = FakeLocalFileDataSource()
        carRepository =
            CarRepositoryImpl(localDataSource = localFileDataSource, dispatcher = testDispatcher)
    }

    //testing success search
    @Test
    fun doSearchByColorAndPrice() = runBlocking {
        val color = "red"
        val price = "200"
        val result = carRepository.doSearchByColorAndPrice(color = color, price = price)
        Assert.assertEquals(result.isNotEmpty(), true)
        // Assert.assertEquals(result.size, 2)
    }

    //testing not matching search
    @Test
    fun doSearchByColorAndPriceEmpty() = runBlocking {
        val color = "value not exist"
        val price = "200"

        val thrown = Assert.assertThrows(
            NoMatchingExceptionException::class.java,
        ) {
            runBlocking {
                carRepository.doSearchByColorAndPrice(color = color, price = price)
            }
        }
        Assert.assertEquals(thrown.message, NoMatchingExceptionException.message)
    }


}