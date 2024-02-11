package com.ame.tictactoe

import com.ame.tictactoe.model.GameStatus
import com.ame.tictactoe.model.Player
import com.ame.tictactoe.model.TicTacGame
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class TicTacGameTest {

    @Test
    fun `Cross will win in horizontal`() {
        val ticTacGame = TicTacGame()

        ticTacGame.addPositionTo(Player.CROSS_PLAYER, Pair(0,0))
        ticTacGame.addPositionTo(Player.CROSS_PLAYER, Pair(0,1))
        ticTacGame.addPositionTo(Player.CROSS_PLAYER, Pair(0,2))

        assertThat(ticTacGame.checkWinner(Player.CROSS_PLAYER)).isEqualTo(GameStatus.CROSS_WINS)
    }

    @Test
    fun `Cross will win in vertical`() {
        val ticTacGame = TicTacGame()

        ticTacGame.addPositionTo(Player.CROSS_PLAYER, Pair(1,0))
        ticTacGame.addPositionTo(Player.CROSS_PLAYER, Pair(1,1))
        ticTacGame.addPositionTo(Player.CROSS_PLAYER, Pair(1,2))

        assertThat(ticTacGame.checkWinner(Player.CROSS_PLAYER)).isEqualTo(GameStatus.CROSS_WINS)
    }

    @Test
    fun `Cross will win cross`() {
        val ticTacGame = TicTacGame()

        ticTacGame.addPositionTo(Player.CROSS_PLAYER, Pair(0,0))
        ticTacGame.addPositionTo(Player.CROSS_PLAYER, Pair(1,1))
        ticTacGame.addPositionTo(Player.CROSS_PLAYER, Pair(2,2))

        assertThat(ticTacGame.checkWinner(Player.CROSS_PLAYER)).isEqualTo(GameStatus.CROSS_WINS)
    }

    @Test
    fun `Cross wont win`() {
        val ticTacGame = TicTacGame()

        ticTacGame.addPositionTo(Player.CROSS_PLAYER, Pair(0,0))
        ticTacGame.addPositionTo(Player.CROSS_PLAYER, Pair(0,1))
        ticTacGame.addPositionTo(Player.CROSS_PLAYER, Pair(2,2))

        assertThat(ticTacGame.checkWinner(Player.CROSS_PLAYER)).isEqualTo(GameStatus.PLAYING)
    }
}