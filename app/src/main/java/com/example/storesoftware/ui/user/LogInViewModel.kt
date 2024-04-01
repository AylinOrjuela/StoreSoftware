package com.example.storesoftware.ui.user

import androidx.lifecycle.ViewModel
import com.example.storesoftware.domain.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LogInViewModel @Inject constructor() : ViewModel() {
    private val user = User.instance
    fun loginAdmin(username: String, password: String): Boolean {
        return user.username == username && user.password == password
    }
}