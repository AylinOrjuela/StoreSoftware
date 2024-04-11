package com.example.storesoftware.ui.product.addproduct

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.storesoftware.data.network.FirebaseDataBaseService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class CreateProductViewModel @Inject constructor(private val repository: FirebaseDataBaseService) :
    ViewModel() {

    private var _uiState = MutableStateFlow<CreateProductState>(CreateProductState())
    val uiState: StateFlow<CreateProductState> = _uiState

    fun onNameChanged(name: CharSequence?) {
        _uiState.update { it.copy(name = name.toString()) }
    }

    fun onPriceChanged(price: CharSequence?) {
        _uiState.update { it.copy(price = price.toString()) }
    }

    fun onDescriptionChanged(description: CharSequence?) {
        _uiState.update { it.copy(description = description.toString()) }
    }

    fun onStockChanged(stock: CharSequence?) {
        _uiState.update { it.copy(stock = stock.toString().toInt()) }
    }

    fun onImageSelected(uri: Uri) {
        viewModelScope.launch {
            showLoading(true)
            val result = withContext(Dispatchers.IO) {
                repository.uploadAndDownloadImage(uri)
            }
            _uiState.update { it.copy(imageUrl = result) }
            showLoading(false)
        }

    }

    private fun showLoading(show: Boolean) {
        _uiState.update { it.copy(isLoading = show) }
    }


    fun onAddProductSelected(onSuccessProduct:()-> Unit) {
        viewModelScope.launch {
            showLoading(true)
            val result = withContext(Dispatchers.IO) {
                repository.uploadNewProduct(
                    _uiState.value.name,
                    _uiState.value.description,
                    _uiState.value.price,
                    _uiState.value.stock,
                    _uiState.value.imageUrl
                )
            }

            if(result){
                onSuccessProduct()
            }else{
                _uiState.update { it.copy(error = "Ha ocurrido un error") }
            }

            showLoading(false)
        }
    }


}

data class CreateProductState(
    val name: String = "",
    val description: String = "",
    val price: String = "",
    val imageUrl: String = "",
    val stock: Int = 0,
    val isLoading: Boolean = false,
    val error: String? = null
) {
    fun isValidProduct() = name.isNotBlank() && description.isNotBlank() && price.isNotBlank()
}