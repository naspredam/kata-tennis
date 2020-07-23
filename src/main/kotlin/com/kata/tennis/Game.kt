package com.kata.tennis

import java.lang.IllegalArgumentException

fun startGame() = Game()

fun createGame(playerOne: Point, playerTwo: Point): Game {
    val score = Score(playerOne, playerTwo)
    return Game(score = score, status = if (score.bothAreOnForty()) Status.DUECE else Status.STARTED)
}

class Game internal constructor(val score: Score? = Score(Point.LOVE, Point.LOVE),
           val status: Status = Status.STARTED,
           val winner: Player? = null) {

    private val inTheLead: Player? = score?.findInLeadPlayer()

    fun pointForPlayerOne(): Game {
        if (score == null) {
            throw IllegalArgumentException("Cannot do points when the game is over...")
        }
        return when {
            isPlayerAboutToWin(Player.ONE) -> Game(score = null, status = Status.FINISHED, winner = Player.ONE)
            status == Status.DUECE -> Game(score = Score(Point.FORTY, Point.THIRTY), status = Status.ADVANTAGE)
            else -> {
                val nextScore = Score(score.playerOne.next(), score.playerTwo)
                return Game(score = nextScore, status = if (nextScore.bothAreOnForty()) Status.DUECE else Status.STARTED)
            }
        }
    }

    fun pointForPlayerTwo(): Game {
        if (score == null) {
            throw IllegalArgumentException("Cannot do points when the game is over...")
        }
        return when {
            isPlayerAboutToWin(Player.TWO) -> Game(score = null, status = Status.FINISHED, winner = Player.TWO)
            status == Status.DUECE -> Game(score = Score(Point.THIRTY, Point.FORTY), status = Status.ADVANTAGE)
            else -> {
                val nextScore = Score(score.playerOne, score.playerTwo.next())
                return Game(score = nextScore, status = if (nextScore.bothAreOnForty()) Status.DUECE else Status.STARTED)
            }
        }
    }

    private fun isPlayerAboutToWin(player: Player) =
        inTheLead == player && score!!.getPointFromPlayer(player) == Point.FORTY && score.difference() >= 1
}