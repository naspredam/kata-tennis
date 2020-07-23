package com.kata.tennis

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class GameKtTest {

    @Test
    fun shouldStartGameWithLoveLove() {
        val game = startGame()
        assertThat(game).isNotNull
        assertThat(game.score).isEqualTo(Score(Point.LOVE, Point.LOVE))
    }

    @Test
    fun shouldStartGameWithInitialState_startedWithNoWinner() {
        val game = startGame()
        assertThat(game).isNotNull
        assertThat(game.status).isEqualTo(Status.STARTED)
        assertThat(game.winner).isNull()
    }

    @Test
    fun shouldMoveToLoveFifteen_whenSecondPlayerDidPoint() {
        val game = startGame()
        val pointForPlayerTwo = game.pointForPlayerTwo()

        assertThat(pointForPlayerTwo).isNotNull
        assertThat(pointForPlayerTwo.score).isEqualTo(Score(Point.LOVE, Point.FIFTEEN))
        assertThat(game.status).isEqualTo(Status.STARTED)
        assertThat(game.winner).isNull()
    }

    @Test
    fun shouldMoveToFifteenLove_whenFirstPlayerDidPoint() {
        val game = startGame()
        val pointForPlayerOne = game.pointForPlayerOne()

        assertThat(pointForPlayerOne).isNotNull
        assertThat(pointForPlayerOne.score).isEqualTo(Score(Point.FIFTEEN, Point.LOVE))
        assertThat(game.status).isEqualTo(Status.STARTED)
        assertThat(game.winner).isNull()
    }

    @Test
    fun shouldMoveToFifteenFifteen_whenBothDidFirstTwoPoints() {
        val game = startGame()
        val pointForPlayerTwo = game.pointForPlayerTwo()
        val fifteenFifteen = pointForPlayerTwo.pointForPlayerOne()

        assertThat(fifteenFifteen).isNotNull
        assertThat(fifteenFifteen.score).isEqualTo(Score(Point.FIFTEEN, Point.FIFTEEN))
        assertThat(game.status).isEqualTo(Status.STARTED)
        assertThat(game.winner).isNull()
    }

    @Test
    fun shouldFirstPlayerWinTheGame_whenTheyHaveMoreThanOnePointDifference() {
        val game = Game(score = Score(Point.FORTY, Point.THIRTY))
        val playerOneWon = game.pointForPlayerOne()

        assertThat(playerOneWon).isNotNull
        assertThat(playerOneWon.status).isEqualTo(Status.FINISHED)
        assertThat(playerOneWon.winner).isEqualTo(Player.ONE)
    }

    @Test
    fun shouldSecondPlayerWinTheGame_whenTheyHaveMoreThanOnePointDifference() {
        val game = Game(score = Score(Point.FIFTEEN, Point.FORTY))
        val playerOneWon = game.pointForPlayerTwo()

        assertThat(playerOneWon).isNotNull
        assertThat(playerOneWon.status).isEqualTo(Status.FINISHED)
        assertThat(playerOneWon.winner).isEqualTo(Player.TWO)
    }

    @Test
    fun shouldGoInDuece_whenBothAreGoingToHaveFortyForty_WithPlayerOneInTheLead() {
        val game = Game(score = Score(Point.FORTY, Point.THIRTY))
        val playerOneWon = game.pointForPlayerTwo()

        assertThat(playerOneWon).isNotNull
        assertThat(playerOneWon.status).isEqualTo(Status.DUECE)
        assertThat(playerOneWon.winner).isNull()
    }

    @Test
    fun shouldGoInDuece_whenBothAreGoingToHaveFortyForty_WithPlayerTwoInTheLead() {
        val game = Game(score = Score(Point.THIRTY, Point.FORTY))
        val playerOneWon = game.pointForPlayerOne()

        assertThat(playerOneWon).isNotNull
        assertThat(playerOneWon.status).isEqualTo(Status.DUECE)
        assertThat(playerOneWon.winner).isNull()
    }

    @Test
    fun shouldGoInAdvance_fromDuece_WithPlayerTwoInTheLead() {
        val game = Game(score = Score(Point.FORTY, Point.FORTY), status = Status.DUECE)
        val playerOneWon = game.pointForPlayerTwo()

        assertThat(playerOneWon).isNotNull
        assertThat(playerOneWon.score).isEqualTo(Score(Point.THIRTY, Point.FORTY))
        assertThat(playerOneWon.status).isEqualTo(Status.ADVANTAGE)
        assertThat(playerOneWon.winner).isNull()
    }

    @Test
    fun shouldGoInAdvance_fromDuece_WithPlayerOneInTheLead() {
        val game = Game(score = Score(Point.FORTY, Point.FORTY), status = Status.DUECE)
        val playerOneWon = game.pointForPlayerOne()

        assertThat(playerOneWon).isNotNull
        assertThat(playerOneWon.score).isEqualTo(Score(Point.FORTY, Point.THIRTY))
        assertThat(playerOneWon.status).isEqualTo(Status.ADVANTAGE)
        assertThat(playerOneWon.winner).isNull()
    }
}