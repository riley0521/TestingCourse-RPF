package com.plcoding.testingcourse.part4.presentation

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNotNull
import com.plcoding.testingcourse.part4.data.FakeAnalyticsLogger
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class GoodProfileViewModelTest {

    private lateinit var analytics: FakeAnalyticsLogger
    private lateinit var viewModel: GoodProfileViewModel

    @BeforeEach
    fun setup() {
        analytics = FakeAnalyticsLogger()
        viewModel = GoodProfileViewModel(
            analytics = analytics
        )
    }

    @Test
    fun `Test save profile assert infoMessage in state not null`() {
        viewModel.saveProfile()

        val firstEvent = analytics.getEvents().first()

        assertThat(firstEvent.key).isEqualTo("save_profile")
        assertThat(viewModel.state.infoMessage).isNotNull()
    }
}