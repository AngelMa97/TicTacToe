package com.ame.tictactoe.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.isVisible
import com.ame.tictactoe.R
import com.ame.tictactoe.databinding.ActivityTicTacToeBinding
import com.ame.tictactoe.model.GameStatus
import com.ame.tictactoe.model.Player
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TicTacToeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTicTacToeBinding
    private val viewModel: TicTacToeViewModel by viewModels()
    private lateinit var circleName: String
    private lateinit var crossName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTicTacToeBinding.inflate(layoutInflater)

        circleName = intent.getStringExtra("match_circle")!!
        crossName = intent.getStringExtra("match_cross")!!

        setView()
        setObservers()

        setContentView(binding.root)
    }

    private fun setView() {
        binding.apply {
            x0y0.setOnClickListener { buttonsFun(it as FloatingActionButton, Pair(0,0)) }
            x1y0.setOnClickListener { buttonsFun(it as FloatingActionButton, Pair(1,0)) }
            x2y0.setOnClickListener { buttonsFun(it as FloatingActionButton, Pair(2,0)) }
            x0y1.setOnClickListener { buttonsFun(it as FloatingActionButton, Pair(0,1)) }
            x1y1.setOnClickListener { buttonsFun(it as FloatingActionButton, Pair(1,1)) }
            x2y1.setOnClickListener { buttonsFun(it as FloatingActionButton, Pair(2,1)) }
            x0y2.setOnClickListener { buttonsFun(it as FloatingActionButton, Pair(0,2)) }
            x1y2.setOnClickListener { buttonsFun(it as FloatingActionButton, Pair(1,2)) }
            x2y2.setOnClickListener { buttonsFun(it as FloatingActionButton, Pair(2,2)) }
            returnButton.setOnClickListener { finish() }
        }
    }

    private fun setObservers() {
        viewModel.apply {
            binding.apply {
                playerTurn.observe(this@TicTacToeActivity) {
                    turnLabel.text = if (it == Player.CIRCLE_PLAYER) "$circleName turn" else "$crossName turn"
                }
                turnNumber.observe(this@TicTacToeActivity) {
                    turnNumberLabel.text = "Turn: $it"
                }
                gameStatus.observe(this@TicTacToeActivity) {
                    if (it != GameStatus.PLAYING) {
                        disableAllButtons()
                        winnerOverlay.isVisible = true
                        if (it == GameStatus.DRAW) {
                            playerWonLabel.text = "DRAW"
                        } else {
                            val player = if(it == GameStatus.CIRCLE_WINS) "Circle" else "Cross"
                            playerWonLabel.text = "$player Player has won"
                        }
                    }
                }
            }
        }
    }

    private fun buttonsFun(v: FloatingActionButton, position: Pair<Int, Int>) {
        v.setImageDrawable(
            AppCompatResources.getDrawable(
                baseContext,
                if (viewModel.playerTurn.value == Player.CIRCLE_PLAYER) R.drawable.ic_circle
                else R.drawable.ic_cross
            )
        )
        v.isEnabled = false
        viewModel.changeTurn(position)
    }

    private fun disableAllButtons() {
        binding.apply {
            x0y0.isEnabled = false
            x1y0.isEnabled = false
            x2y0.isEnabled = false
            x0y1.isEnabled = false
            x1y1.isEnabled = false
            x2y1.isEnabled = false
            x0y2.isEnabled = false
            x1y2.isEnabled = false
            x2y2.isEnabled = false
        }
    }
}