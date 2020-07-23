package com.kata.tennis

import java.lang.IllegalArgumentException
import kotlin.math.abs

enum class Player {ONE, TWO}

enum class Point {
    LOVE, FIFTEEN, THIRTY, FORTY;

    fun next(): Point {
        val indexOf = values().indexOf(this)
        return if (this == FORTY) throw IllegalArgumentException("Not allowed to go after forty...")
            else values()[indexOf + 1]
    }
}

data class Score(val playerOne: Point, val playerTwo: Point) {

    fun getPointFromPlayer(player: Player) = if (player == Player.ONE) playerOne else playerTwo

    fun findInLeadPlayer() = when {
        playerOne.ordinal > playerTwo.ordinal -> Player.ONE
        playerOne.ordinal < playerTwo.ordinal -> Player . TWO
        else -> null
    }

    fun difference() = abs(playerOne.ordinal - playerTwo.ordinal)

    fun bothAreOnForty () = playerOne == Point.FORTY && playerTwo == Point.FORTY
}
