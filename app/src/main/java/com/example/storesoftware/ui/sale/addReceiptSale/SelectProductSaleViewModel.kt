package com.example.storesoftware.ui.sale.addReceiptSale

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
class SelectProductSaleViewModel @Inject constructor(val repository: FirebaseDataBaseService) : ViewModel(){
    private var _uiState = MutableStateFlow<UIMainProductState>(UIMainProductState())
    val uiState: StateFlow<UIMainProductState> = _uiState

    init {
        getdata()
    }

    private fun getdata() {
        getAllProducts()
    }

    private fun getAllProducts() {
        viewModelScope.launch {
            val response = withContext(Dispatchers.IO){
                repository.getAllProducts()
            }
            _uiState.update { it.copy(products = response) }
        }
    }


}
data class UIMainProductState(
    val products: List<Product> = emptyList()
)