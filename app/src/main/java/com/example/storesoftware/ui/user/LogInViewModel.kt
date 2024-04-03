package com.example.storesoftware.ui.user

import androidx.lifecycle.ViewModel
import com.example.storesoftware.data.network.FirebaseDataBaseService
import com.example.storesoftware.domain.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LogInViewModel @Inject constructor(private val firebaseDataBaseService: FirebaseDataBaseService) : ViewModel() {

    fun loginUser(username: String, password: String, callback: (String) -> Unit) {
        firebaseDataBaseService.findUserByCredentials(username, password) { userId ->
            callback(userId)
        }
    }
}