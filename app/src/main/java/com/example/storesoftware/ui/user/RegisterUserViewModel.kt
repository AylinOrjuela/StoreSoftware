package com.example.storesoftware.ui.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.storesoftware.data.network.FirebaseDataBaseService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterUserViewModel @Inject constructor(private val firebaseDataBaseService: FirebaseDataBaseService) : ViewModel() {
    fun createUser(
        firstName: String,
        lastName: String,
        cc: String,
        username: String,
        password: String,
        code: String
    ) {
       viewModelScope.launch {
           firebaseDataBaseService.setNewUser(firstName,lastName,cc,username,password,code)
       }
    }
}