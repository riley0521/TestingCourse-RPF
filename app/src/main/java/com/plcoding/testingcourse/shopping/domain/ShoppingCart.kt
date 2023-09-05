package com.plcoding.testingcourse.shopping.domain

import com.plcoding.testingcourseexamples.part1.domain.ShoppingCartCache
import kotlin.random.Random

class ShoppingCartCacheDummy: ShoppingCartCache {
    override fun saveCart(items: List<Product>) {

    }

    override fun loadCart(): List<Product> {
        return emptyList()
    }

    override fun clearCart() {

    }
}

class ShoppingCartCacheStub: ShoppingCartCache {
    override fun saveCart(items: List<Product>) {

    }

    override fun loadCart(): List<Product> {
        return (1..4).map {
            Product(
                id = it,
                name = "Product $it",
                price = Random.nextDouble(10.0, 100.0)
            )
        }
    }

    override fun clearCart() {

    }
}

/**
 * We often use Fake and Mocks (Decide what functions return what)
 *
 * 3 Types of assertion in testing.
 *      1. Verify the output of the function
 *      2. Verify the current state of the system/variable based on the action done
 *      3. Verifying communication - Check if the 2 or more different classes communicated with each other
 *
 * Test Driven Development (TDD)
 * Should I apply this often right off the bat?
 * Normal steps:
 *      1. Write the function signature without any implementation
 *      2. Write the test cases
 *      3. Then you can finally write the function implementation
 *
 * Advantages:
 *      1. Your code is well tested
 *      2. Your code will truly follow the 'clean architecture' because you cannot test bad code.
 *      3. You can foresee the potential bugs and edge cases.
 *
 * Disadvantages:
 *      1. Steep learning curve.
 *      2. It only makes sense for unit tests? How about instrumented and end-to-end tests?
 *
 * What makes the code testable?
 *
 *      1. High cohesion (The class should have related functions but 1 single responsibility)
 *      2. Low coupling (Less class dependency since it will be harder to test single class in isolation)
 *      If you cannot avoid coupling (class dependency) then use abstraction (interface or abstract class)
 *      3. Use dependency injection
 */
class ShoppingCartCacheFake: ShoppingCartCache {

    private var products = mutableListOf<Product>()

    override fun saveCart(items: List<Product>) {
        products = items.toMutableList()
    }

    override fun loadCart(): List<Product> {
        return products
    }

    override fun clearCart() {
        products.clear()
    }
}



class ShoppingCart(
    private val cache: ShoppingCartCache
) {

    private val validProductIds = listOf(1, 2, 3, 4, 5)
    private val items = mutableListOf<Product>()

    fun addProduct(product: Product, quantity: Int) {
        if(quantity < 0) {
            throw IllegalArgumentException("Quantity can't be negative")
        }
        if (isValidProduct(product)) {
            repeat(quantity) {
                items.add(product)
            }
            cache.saveCart(items)
        }
    }

    private fun isValidProduct(product: Product): Boolean {
        return product.price >= 0.0 && product.id in validProductIds
    }

    fun getTotalCost(): Double {
        return items.sumOf { it.price }
    }
}