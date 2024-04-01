package com.example.storesoftware.ui.Store

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.storesoftware.data.network.FirebaseDataBaseService
import com.example.storesoftware.domain.model.Store
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterStoreViewModel @Inject constructor(private val firebaseDataBaseService: FirebaseDataBaseService) :
    ViewModel() {

    private var _uiState = MutableStateFlow(RegisterStoreState())
    fun onNameChanged(name: CharSequence?) {
        _uiState.update { it.copy(name = name.toString()) }
    }

    fun onAddressChanged(address: CharSequence?) {
        _uiState.update { it.copy(address = address.toString()) }
    }

    fun onRegisterStoreSelected() {
        viewModelScope.launch {
            firebaseDataBaseService.uploadNewStore(
                _uiState.value.name,
                _uiState.value.address
            )
        }
    }

    suspend fun onAlreadyRegisterStore(): Store? {
        return firebaseDataBaseService.getStoreData()
    }

}

data class RegisterStoreState(
    val name: String = "",
    val address: String = ""
)