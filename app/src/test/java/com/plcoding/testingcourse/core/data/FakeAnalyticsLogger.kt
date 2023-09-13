package com.plcoding.testingcourse.core.data

import com.plcoding.testingcourse.core.domain.AnalyticsLogger
import com.plcoding.testingcourse.core.domain.LogParam

class FakeAnalyticsLogger : AnalyticsLogger {

    var events = mutableListOf<Event>()

    override fun logEvent(key: String, vararg params: LogParam<Any>) {
        events.add(Event(key, params.toList()))
    }
}

data class Event(
    val key: String,
    val params: List<LogParam<Any>>
)