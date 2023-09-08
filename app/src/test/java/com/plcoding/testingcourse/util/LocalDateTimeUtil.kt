package com.plcoding.testingcourse.util

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceTimeBy
import kotlin.time.Duration

@OptIn(ExperimentalCoroutinesApi::class)
fun TestScope.advanceTimeBy(duration: Duration, clock: MutableClock) {
    advanceTimeBy(duration)
    clock.advanceTimeBy(duration)
}