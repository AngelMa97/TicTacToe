package com.ame.tictactoe.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ame.tictactoe.model.GameStatus
import com.ame.tictactoe.model.Player
import com.ame.tictactoe.model.TicTacGame
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TicTacToeViewModel @Inject constructor() : ViewModel() {

    private var _playerTurn = MutableLiveData(Player.CIRCLE_PLAYER)
    val playerTurn: LiveData<Player> get() = _playerTurn

    private var _turnNumber = MutableLiveData(1)
    val turnNumber: LiveData<Int> get() = _turnNumber

    private var _gameStatus = MutableLiveData<GameStatus>(GameStatus.PLAYING)
    val gameStatus: LiveData<GameStatus> get() = _gameStatus

    private val ticTacGame = TicTacGame()

    fun changeTurn(takenPosition: Pair<Int, Int>) {
        val playerTurnValue = _playerTurn.value!!
        ticTacGame.addPositionTo(
            playerTurnValue,
            takenPosition
        )
        val newTurn = _turnNumber.value!! + 1
        _turnNumber.postValue(newTurn)
        if (newTurn > 5) {
            val gameFinished = ticTacGame.checkWinner(playerTurnValue)
            _gameStatus.postValue(gameFinished)
            if (newTurn == 10 && gameFinished == GameStatus.PLAYING) {
                _gameStatus.postValue(GameStatus.DRAW)
            }
        }
        _playerTurn.postValue(if (playerTurnValue == Player.CIRCLE_PLAYER) Player.CROSS_PLAYER else Player.CIRCLE_PLAYER)
    }

}