package com.plcoding.testingcourse.part4.data

import com.plcoding.testingcourse.part4.domain.AnalyticsLogger
import com.plcoding.testingcourse.part4.domain.LogParam

class FakeAnalyticsLogger : AnalyticsLogger {

    private var _events = mutableListOf<Event>()

    override fun logEvent(key: String, vararg params: LogParam<Any>) {
        _events.add(Event(key = key, params = params.toList()))
    }

    fun getEvents(): List<Event> {
        return _events
    }
}

data class Event(
    val key: String,
    val params: List<LogParam<Any>>
)