package com.ame.tictactoe.di

import android.app.Application
import androidx.room.Room
import com.ame.tictactoe.model.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DbModule {

    @Provides
    @Singleton
    fun databaseProvider(app: Application) =
        Room.databaseBuilder(app, AppDatabase::class.java, "tictactoe_db")
        .fallbackToDestructiveMigration().build()

}