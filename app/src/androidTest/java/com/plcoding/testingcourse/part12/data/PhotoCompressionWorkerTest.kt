package com.plcoding.testingcourse.part12.data

import assertk.assertThat
import assertk.assertions.isTrue
import com.plcoding.testingcourse.R
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

class PhotoCompressionWorkerTest {

    @get:Rule
    val contentUriRule = TestContentUriRule(R.drawable.kermit)

    @Test
    fun testContentUri() {
        println(contentUriRule.contentUri)

        assertThat(true).isTrue()
    }
}