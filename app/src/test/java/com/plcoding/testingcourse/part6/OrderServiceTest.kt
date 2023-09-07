package com.plcoding.testingcourse.part6

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkConstructor
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class OrderServiceTest {

    private lateinit var orderService: OrderService

    private lateinit var auth: FirebaseAuth
    private lateinit var emailClient: EmailClient

    @BeforeEach
    fun setup() {
        val firebaseUser = mockk<FirebaseUser>(relaxed = true) {
            every { isAnonymous } returns false
        }

        auth = mockk(relaxed = true) {
            every { currentUser } returns firebaseUser
        }
        emailClient = mockk(relaxed = true)

        orderService = OrderService(auth, emailClient)
    }

    @Test
    fun `Place order, user not anonymous, send the correct email`() {
        orderService.placeOrder("test@test.com", "Apple")

        verify {
            emailClient.send(
                Email(
                    subject = "Order Confirmation",
                    content = "Thank you for your order of Apple.",
                    recipient = "test@test.com"
                )
            )
        }
    }
}