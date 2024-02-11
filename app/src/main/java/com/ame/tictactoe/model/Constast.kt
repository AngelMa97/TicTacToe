package com.ame.tictactoe.model

enum class Player {
    CROSS_PLAYER,
    CIRCLE_PLAYER
}

enum class GameStatus {
    PLAYING,
    CROSS_WINS,
    CIRCLE_WINS,
    DRAW
}