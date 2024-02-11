package com.ame.tictactoe.model

class TicTacGame {

    private val crossTaken: MutableList<Pair<Int, Int>> = mutableListOf()
    private val circleTaken: MutableList<Pair<Int, Int>> = mutableListOf()

    fun addPositionTo(playerTurn: Player, position: Pair<Int, Int>) {
        if (playerTurn == Player.CIRCLE_PLAYER) circleTaken.add(position)
        else crossTaken.add(position)
    }

    fun checkWinner(playerTurn: Player): GameStatus {
        val playerWon = check(
            if (playerTurn == Player.CIRCLE_PLAYER) circleTaken
            else crossTaken
        )
        return if (playerWon) {
            if (playerTurn == Player.CIRCLE_PLAYER) GameStatus.CIRCLE_WINS
            else GameStatus.CROSS_WINS
        } else {
            GameStatus.PLAYING
        }
    }

    private fun check(playerList: MutableList<Pair<Int, Int>>): Boolean {
        playerList.let {
            if (it.find { x -> x.first == 1 && x.second == 1 } != null) {
                if (it.count { x -> x.first == 1 } == 3 ||
                    it.count { x -> x.second == 1 } == 3) {
                    return true
                }
                if (
                    it.find { x -> x.first == 0 && x.second == 0 } != null &&
                    it.find { x -> x.first == 2 && x.second == 2 } != null
                ) {
                    return true
                }
                if (
                    it.find { x -> x.first == 0 && x.second == 2 } != null &&
                    it.find { x -> x.first == 2 && x.second == 0 } != null
                ) {
                    return true
                }
            } else {
                if (
                    it.count { x -> x.first == 0 } == 3 ||
                    it.count { x -> x.first == 2 } == 3 ||
                    it.count { x -> x.second == 0 } == 3 ||
                    it.count { x -> x.second == 2 } == 3
                ) {
                    return true
                }
            }
        }
        return false
    }
}