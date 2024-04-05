package com.example.storesoftware.ui.user

import androidx.lifecycle.ViewModel
import com.example.storesoftware.data.network.FirebaseDataBaseService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.example.storesoftware.domain.model.User

@HiltViewModel
class ConsultUserViewModel @Inject constructor(private val firebaseDataBaseService: FirebaseDataBaseService) :
    ViewModel() {
    suspend fun getUserById(userId: String): User? {
        return firebaseDataBaseService.getUserById(userId)
    }

}