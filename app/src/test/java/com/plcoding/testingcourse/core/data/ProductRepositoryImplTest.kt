package com.plcoding.testingcourse.core.data

import assertk.assertThat
import assertk.assertions.isTrue
import com.plcoding.testingcourse.core.domain.AnalyticsLogger
import com.plcoding.testingcourse.core.domain.LogParam
import com.plcoding.testingcourse.core.domain.Product
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkConstructor
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import retrofit2.HttpException

internal class ProductRepositoryImplTest {

    private lateinit var repository: ProductRepositoryImpl
    private lateinit var productApi: ProductApi
    private lateinit var analyticsLogger: AnalyticsLogger

    @BeforeEach
    fun setup() {
        productApi = mockk()
        analyticsLogger = mockk(relaxed = true)
        repository = ProductRepositoryImpl(productApi, analyticsLogger)
    }

    @Test
    fun `Response error, exception is logged`() = runBlocking<Unit> {
//        every { analyticsLogger.logEvent(any(), *anyVararg()) } answers {
//            println("Analytics is called.")
//        }
        coEvery { productApi.purchaseProducts(any()) } throws mockk<HttpException> {
            every { code() } returns 404
            every { message() } returns "Not found"
        }

        // Not relevant to this test, but example for mockkConstructor
        mockkConstructor(Product::class)
        every { anyConstructed<Product>().name } returns "Mocked"

        val result = repository.purchaseProducts(listOf())

        assertThat(result.isFailure).isTrue()

        verify(exactly = 1) {
            analyticsLogger.logEvent(
                "http_error",
                LogParam("code", 404),
                LogParam("message", "Not found")
            )
        }
    }
}