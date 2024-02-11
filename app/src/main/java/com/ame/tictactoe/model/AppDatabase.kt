package com.ame.tictactoe.model

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ame.tictactoe.model.dao.MatchDao
import com.ame.tictactoe.model.dao.UserDao
import com.ame.tictactoe.model.entity.Match
import com.ame.tictactoe.model.entity.User

@Database(
    entities = [User::class, Match::class],
    version = 1
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun matchDao(): MatchDao
}