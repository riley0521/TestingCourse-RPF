package com.plcoding.testingcourse.part4.presentation

import assertk.assertThat
import assertk.assertions.hasSize
import assertk.assertions.isEqualTo
import assertk.assertions.isNotNull
import com.plcoding.testingcourse.core.data.Event
import com.plcoding.testingcourse.core.data.FakeAnalyticsLogger
import com.plcoding.testingcourse.core.domain.LogParam
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class GoodProfileViewModelTest {

    private lateinit var viewModel: GoodProfileViewModel

    private lateinit var analyticsLogger: FakeAnalyticsLogger

    @BeforeEach
    fun setUp() {
        analyticsLogger = FakeAnalyticsLogger()

        viewModel = GoodProfileViewModel(
            analytics = analyticsLogger
        )
    }

    @Test
    fun `Test save profile, analytics is logged`() {
        viewModel.saveProfile()

        assertThat(analyticsLogger.events.first().key).isEqualTo("save_profile")
        assertThat(analyticsLogger.events.first().params).hasSize(2)

        val state = viewModel.state

        assertThat(state.infoMessage).isNotNull()
    }
}