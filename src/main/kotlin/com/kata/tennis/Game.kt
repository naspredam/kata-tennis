package com.kata.tennis

import java.lang.IllegalArgumentException

class Game private constructor(val score: Score?,
                                val status: Status,
                                val winner: Player? = null) {

    private val inTheLead: Player? = score?.findInLeadPlayer()

    companion object {
        fun new() = Game(score = Score(Point.LOVE, Point.LOVE), status = Status.STARTED)

        fun from(playerOne: Point, playerTwo: Point): Game {
            val score = Score(playerOne, playerTwo)
            return Game(score = score, status = if (score.bothAreOnForty()) Status.DUECE else Status.STARTED)
        }
    }

    fun pointForPlayerOne(): Game {
        if (score == null) {
            throw IllegalArgumentException("Cannot do points when the game is over...")
        }
        return when {
            isPlayerAboutToWin(Player.ONE) -> Game(score = null, status = Status.FINISHED, winner = Player.ONE)
            status == Status.DUECE -> Game(score = Score(Point.FORTY, Point.THIRTY), status = Status.ADVANTAGE)
            else -> from(score.playerOnePoints.next(), score.playerTwoPoints)
        }
    }

    fun pointForPlayerTwo(): Game {
        if (score == null) {
            throw IllegalArgumentException("Cannot do points when the game is over...")
        }
        return when {
            isPlayerAboutToWin(Player.TWO) -> Game(score = null, status = Status.FINISHED, winner = Player.TWO)
            status == Status.DUECE -> Game(score = Score(Point.THIRTY, Point.FORTY), status = Status.ADVANTAGE)
            else -> from(score.playerOnePoints, score.playerTwoPoints.next())
        }
    }

    private fun isPlayerAboutToWin(player: Player) =
        inTheLead == player && score!!.getPointFromPlayer(player).isForty() && score.difference() >= 1
}