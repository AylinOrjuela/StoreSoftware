package com.example.storesoftware.ui.user.editDeleteUser

import androidx.lifecycle.ViewModel
import com.example.storesoftware.data.network.FirebaseDataBaseService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EditDeleteUserViewModel @Inject constructor(private val firebaseDataBaseService: FirebaseDataBaseService) :
    ViewModel() {
}