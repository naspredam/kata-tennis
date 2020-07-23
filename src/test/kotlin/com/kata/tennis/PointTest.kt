package com.kata.tennis

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class PointTest {

    @Test
    fun shouldFifteenBeAfterLove() {
        val afterLove = Point.LOVE.next()
        assertThat(afterLove).isEqualTo(Point.FIFTEEN)
    }

    @Test
    fun shouldThirtyBeAfterFifteen() {
        val afterLove = Point.FIFTEEN.next()
        assertThat(afterLove).isEqualTo(Point.THIRTY)
    }

    @Test
    fun shouldFortyBeAfterThirty() {
        val afterLove = Point.THIRTY.next()
        assertThat(afterLove).isEqualTo(Point.FORTY)
    }
}