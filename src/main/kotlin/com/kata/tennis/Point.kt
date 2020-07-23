package com.kata.tennis

import java.lang.IllegalArgumentException
import kotlin.math.abs
import kotlin.math.sign

enum class Player {ONE, TWO}

enum class Point {
    LOVE, FIFTEEN, THIRTY, FORTY;

    fun isForty() = this == FORTY

    fun next(): Point {
        val indexOf = values().indexOf(this)
        return if (this == FORTY) throw IllegalArgumentException("Not allowed to go after forty...")
            else values()[indexOf + 1]
    }
}

data class Score(val playerOnePoints: Point, val playerTwoPoints: Point) {

    fun getPointFromPlayer(player: Player) =
        if (player == Player.ONE) playerOnePoints else playerTwoPoints

    fun findInLeadPlayer() =
        when (sign(playerOnePoints.ordinal.toDouble() - playerTwoPoints.ordinal)) {
            1.0 -> Player.ONE
            -1.0 -> Player.TWO
            else -> null
        }

    fun difference() = abs(playerOnePoints.ordinal - playerTwoPoints.ordinal)

    fun bothAreOnForty () = playerOnePoints.isForty() && playerTwoPoints.isForty()
}
