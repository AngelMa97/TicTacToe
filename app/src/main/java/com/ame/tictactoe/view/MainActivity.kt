package com.ame.tictactoe.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import com.ame.tictactoe.databinding.ActivityMainBinding
import com.ame.tictactoe.databinding.PlayerNameLayoutBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "match")
    private val MATCH_CIRCLE = stringPreferencesKey("match_circle")
    private val MATCH_CROSS = stringPreferencesKey("match_cross")
    private val IS_SAVING_DATA = booleanPreferencesKey("is_saving_data")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        binding.newGameButton.setOnClickListener {
            showDialog()
        }

        viewModel.getUsers()

        setContentView(binding.root)
    }

    private fun showDialog() {
        //TODO cambiar toda la funcionalidad de dialog a un fragmentDialog
        val dialogBinding = PlayerNameLayoutBinding.inflate(layoutInflater)
        viewModel.apply {
            dialogBinding.apply {
                lifecycleScope.launch {
                    Log.e("TICTACTOE", "reading data")
                    var isSaved = false
                    val valueSaved = baseContext.dataStore.data.collect() { preferences ->
                        isSaved = preferences[IS_SAVING_DATA] ?: false
                        Log.e("TICTACTOE", "isSavingData= $isSaved")
                        if (isSaved) {
                            circlePlayerName.editText?.setText(preferences[MATCH_CIRCLE])
                            crossPlayerName.editText?.setText(preferences[MATCH_CROSS])
                        }
                    }
                    saveMatch.isSelected = isSaved
                }
                MaterialAlertDialogBuilder(this@MainActivity)
                    .setTitle("Insert name")
                    .setView(root)
                    .setPositiveButton("Accept") { _, _ ->
                        val circleName = circlePlayerName.editText?.text.toString()
                        if (circleName.isNotBlank()) {
                            if (!userFound(circleName)) addUser(circleName)
                        }
                        val crossName = crossPlayerName.editText?.text.toString()
                        if (crossName.isNotBlank()) {
                            if (!userFound(crossName)) addUser(crossName)
                        }
                        if (saveMatch.isActivated) {
                            runBlocking {
                                baseContext.dataStore.edit { settings ->
                                    Log.e("TICTACTOE", "Saving data")
                                    settings[MATCH_CIRCLE] = circleName
                                    settings[MATCH_CROSS] = crossName
                                }
                            }
                        }
                        val gameIntent = Intent(baseContext, TicTacToeActivity::class.java)
                        gameIntent.putExtra("match_circle", circleName)
                        gameIntent.putExtra("match_cross", crossName)
                        startActivity(gameIntent)
                    }
                    .setNegativeButton("Cancel") { listener, _ ->
                        listener.dismiss()
                    }.show()
            }
        }
    }

}