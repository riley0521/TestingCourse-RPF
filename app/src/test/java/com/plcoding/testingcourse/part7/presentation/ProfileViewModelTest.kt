package com.plcoding.testingcourse.part7.presentation

import androidx.lifecycle.SavedStateHandle
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isFalse
import assertk.assertions.isNull
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
}