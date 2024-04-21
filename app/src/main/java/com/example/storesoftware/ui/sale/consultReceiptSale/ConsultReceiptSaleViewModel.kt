package com.example.storesoftware.ui.sale.consultReceiptSale

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.storesoftware.data.network.FirebaseDataBaseService
import com.example.storesoftware.domain.model.Sale
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ConsultReceiptSaleViewModel @Inject constructor(val repository: FirebaseDataBaseService) :
    ViewModel() {
    private var _uiState = MutableStateFlow(UIMainSaleState())
    val uiState: StateFlow<UIMainSaleState> = _uiState

    init {
        getdata()
    }

    private fun getdata() {
        getAllSaleReceipt()
    }
    private fun getAllSaleReceipt(){
        showLoading(true)
        viewModelScope.launch {
            val response = withContext(Dispatchers.IO){
                repository.getListSaleReceipts()
            }
            _uiState.update { it.copy(listSale = response) }
            showLoading(false)
        }
    }
    private fun showLoading(show: Boolean) {
        _uiState.update { it.copy(isLoading = show) }
    }

}

data class UIMainSaleState(
    val listSale: List<Sale> = emptyList(),
    val isLoading: Boolean = false
)