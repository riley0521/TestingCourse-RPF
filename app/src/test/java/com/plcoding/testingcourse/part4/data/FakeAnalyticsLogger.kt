package com.plcoding.testingcourse.part4.data

import com.plcoding.testingcourse.part4.domain.AnalyticsLogger
import com.plcoding.testingcourse.part4.domain.LogParam

class FakeAnalyticsLogger : AnalyticsLogger {

    private val events = mutableListOf<Event>()

    override fun logEvent(key: String, vararg params: LogParam<Any>) {
        events.add(
            Event(
                key = key,
                params = params.toList()
            )
        )
    }
}

data class Event(
    val key: String,
    val params: List<LogParam<Any>>
)