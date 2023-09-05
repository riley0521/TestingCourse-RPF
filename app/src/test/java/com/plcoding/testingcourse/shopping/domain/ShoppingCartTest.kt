package com.plcoding.testingcourse.shopping.domain

import assertk.assertFailure
import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.RepeatedTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.ValueSource

class ShoppingCartTest {

    private lateinit var cart: ShoppingCart

    @BeforeEach
    fun setup() {
        cart = ShoppingCart(
            cache = ShoppingCartCacheFake()
        )
    }

//    @Test
//    fun `Add multiple products, total price sum is correct`() {
//        val product = Product(id = 1, name = "Apple", price = 5.0)
//        cart.addProduct(product, 3)
//
//        val priceSum = cart.getTotalCost()
//
//        assertThat(priceSum).isEqualTo(15.0)
//    }

    @ParameterizedTest
    @CsvSource(
        "3,15.0",
        "0,0.0",
        "4,20.0",
        "1,5.0"
    )
    fun `Add multiple products, total price sum is correct`(
        quantity: Int,
        expectedPriceSum: Double
    ) {
        val product = Product(id = 1, name = "Apple", price = 5.0)
        cart.addProduct(product, quantity)

        val priceSum = cart.getTotalCost()

        assertThat(priceSum).isEqualTo(expectedPriceSum)
    }

    @RepeatedTest(100)
    fun `Add product with negative quantity, throws Exception`() {
        val product = Product(id = 1, name = "Apple", price = 5.0)

        assertFailure {
            cart.addProduct(product, -1)
        }
    }

    @Test
    fun `isValidProduct returns false for invalid productId`() {
        val product = Product(id = 1234, name = "Apple", price = 5.0)
        cart.addProduct(product, 4)

        val priceSum = cart.getTotalCost()

        assertThat(priceSum).isEqualTo(0.0)
    }
}