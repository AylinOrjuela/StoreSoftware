package com.example.storesoftware.ui.product.editDeleteProduct

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.storesoftware.data.network.FirebaseDataBaseService
import com.example.storesoftware.domain.model.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class SelectProductViewModel @Inject constructor(val repository: FirebaseDataBaseService) :
    ViewModel() {
    private var _uiState = MutableStateFlow<UIMainProductState>(UIMainProductState())
    val uiState: StateFlow<UIMainProductState> = _uiState

    init {
        getdata()
    }

    fun getdata() {
        getAllProducts()
    }

    private fun getAllProducts() {
        viewModelScope.launch {
            showLoading(true)
            val response = withContext(Dispatchers.IO) {
                repository.getAllProducts()
            }
            _uiState.update { it.copy(products = response) }
            showLoading(false)
        }
    }
    private fun showLoading(show: Boolean) {
        _uiState.update { it.copy(isLoading = show) }
    }
}

data class UIMainProductState(
    val products: List<Product> = emptyList(),
    val isLoading: Boolean = false
)