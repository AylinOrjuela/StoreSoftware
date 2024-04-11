package com.example.storesoftware.ui.user.editDeleteUser

import androidx.lifecycle.ViewModel
import com.example.storesoftware.data.network.FirebaseDataBaseService
import com.example.storesoftware.domain.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EditDeleteUserViewModel @Inject constructor(private val firebaseDataBaseService: FirebaseDataBaseService) :
    ViewModel() {

    suspend fun getUserById(userId: String): User? {
        return firebaseDataBaseService.getUserById(userId)
    }

    fun updateUser(user:User){
        firebaseDataBaseService.updateUser(user)
    }

    fun deleteUser(userId:String){
        firebaseDataBaseService.deleteUser(userId)
    }

}