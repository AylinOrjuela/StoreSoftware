package com.example.storesoftware.ui.product.HomeProduct

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.storesoftware.data.network.FirebaseDataBaseService
import com.example.storesoftware.data.response.UserResponse
import com.example.storesoftware.domain.model.Product
import com.example.storesoftware.domain.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainProductViewModel @Inject constructor(val repository: FirebaseDataBaseService) :
    ViewModel() {
    private var _uiState = MutableStateFlow<UIMainProductState>(UIMainProductState())
    val uiState: StateFlow<UIMainProductState> = _uiState

    init {
        getdata()
    }

    fun getdata() {
        getLastProduct()
        getAllProducts()
    }

    private fun getAllProducts() {
        viewModelScope.launch {
            val response = withContext(Dispatchers.IO) {
                repository.getAllProducts()
            }

            _uiState.update { it.copy(products = response) }

            getTopProducts(response)
        }
    }

    private fun getTopProducts(products: List<Product>) {
        viewModelScope.launch {
            val response = withContext(Dispatchers.IO){
                repository.getTopProducts()
            }
            val topProducts = products.filter { response.contains(it.id) }
            _uiState.update { it.copy (topProducts = topProducts) }
        }
    }

    private fun getLastProduct() {
        viewModelScope.launch {
            val response = withContext(Dispatchers.IO) {
                repository.getLastProduct()
            }
            //   _uiState.value = _uiState.value.copy(lastProduct = response)
            _uiState.update { it.copy(lastProduct = response) }
        }
    }

    fun checkUserPermissions(userId: String?) {
        viewModelScope.launch {
            val isUserAdmin = userId?.let { repository.checkUserPermissions(it) } ?: false
            val isButtonEnabled = isUserAdmin
            _uiState.update { it.copy(isEditDeleteButtonEnabled = isButtonEnabled) }
        }
    }


}

data class UIMainProductState(
    val lastProduct: Product? = null,
    val products: List<Product> = emptyList(),
    val topProducts: List<Product> = emptyList(),
    val isEditDeleteButtonEnabled: Boolean = true
)