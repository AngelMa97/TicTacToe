package com.ame.tictactoe.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.ame.tictactoe.model.entity.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getAll(): Flow<List<User>>

    @Insert
    suspend fun insertUser(user: User)
}