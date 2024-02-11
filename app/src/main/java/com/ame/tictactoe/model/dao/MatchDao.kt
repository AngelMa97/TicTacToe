package com.ame.tictactoe.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.ame.tictactoe.model.entity.Match

@Dao
interface MatchDao {
    @Query("SELECT * FROM `match` WHERE winner = (:id)")
    fun getWinMatches(id: Int): List<Match>

    @Query("SELECT * FROM `Match` WHERE loser = (:id)")
    fun getLossMatches(id: Int): List<Match>

    @Insert
    fun insertMatch(match: Match)
}