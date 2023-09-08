package com.plcoding.testingcourse.part7.presentation

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isFalse
import assertk.assertions.isNotNull
import assertk.assertions.isNull
import assertk.assertions.isTrue
import com.plcoding.testingcourse.part7.data.FakeUserRepository
import com.plcoding.testingcourse.util.MainCoroutineExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(MainCoroutineExtension::class)
internal class ProfileViewModelTest {

    private lateinit var viewModel: ProfileViewModel

    private lateinit var userRepository: FakeUserRepository

    @BeforeEach
    fun setup() {

        userRepository = FakeUserRepository()

        viewModel = ProfileViewModel(
            repository = userRepository,
            savedStateHandle = SavedStateHandle(
                mapOf(
                    "userId" to userRepository.profileToReturn.user.id
                )
            )
        )
    }

    @Test
    fun `Test loading profile success`() = runTest {
        viewModel.loadProfile()

        advanceUntilIdle()

        assertThat(viewModel.state.value.profile).isEqualTo(userRepository.profileToReturn)
        assertThat(viewModel.state.value.isLoading).isFalse()
    }

    @Test
    fun `Test loading profile error`() = runTest {
        userRepository.errorToReturn = Exception("Test exception")

        viewModel.loadProfile()

        advanceUntilIdle()

        assertThat(viewModel.state.value.profile).isNull()
        assertThat(viewModel.state.value.errorMessage).isEqualTo("Test exception")
        assertThat(viewModel.state.value.isLoading).isFalse()
    }

    @Test
    fun `Test loading state updates`() = runTest {
        viewModel.state.test {
            val emission1 = awaitItem()

            assertThat(emission1.isLoading).isFalse()

            viewModel.loadProfile()

            val emission2 = awaitItem()

            assertThat(emission2.isLoading).isTrue()

            val emission3 = awaitItem()
            assertThat(emission3.isLoading).isFalse()
            assertThat(emission3.profile).isNotNull()
            assertThat(emission3.errorMessage).isNull()
        }
    }
}