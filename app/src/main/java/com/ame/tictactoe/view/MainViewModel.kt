package com.ame.tictactoe.view

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ame.tictactoe.model.AppDatabase
import com.ame.tictactoe.model.entity.User
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val db: AppDatabase,
    @ApplicationContext private val appContext: Context
) : ViewModel() {

    private var _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> get() = _users

    fun getUsers() {
        viewModelScope.launch {
            db.userDao().getAll().collect() {
                _users.postValue(it)
            }
        }
    }

    fun userFound(userName: String): Boolean =
        _users.value?.find { x -> x.name == userName } != null

    fun addUser(userName: String) {
        viewModelScope.launch {
            db.userDao().insertUser(User(0, userName))
        }
    }

}