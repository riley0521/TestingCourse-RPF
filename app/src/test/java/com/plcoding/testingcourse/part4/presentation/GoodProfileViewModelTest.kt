package com.plcoding.testingcourse.part4.presentation

import assertk.assertThat
import assertk.assertions.isNotNull
import com.plcoding.testingcourse.part4.data.FakeAnalyticsLogger
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class GoodProfileViewModelTest {

    private lateinit var viewModel: GoodProfileViewModel

    @BeforeEach
    fun setUp() {
        viewModel = GoodProfileViewModel(
            analytics = FakeAnalyticsLogger()
        )
    }

    @Test
    fun `Test save profile infoMessage not null`() {
        viewModel.saveProfile()

        assertThat(viewModel.state.infoMessage).isNotNull()
    }
}