package com.example.storesoftware.ui.sale.addReceiptSale

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.storesoftware.data.network.FirebaseDataBaseService
import com.example.storesoftware.domain.model.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CreateSaleViewModel @Inject constructor(private val repository: FirebaseDataBaseService) :
    ViewModel() {

    suspend fun getProductByid(product: String): Product? {
        return repository.getProductById(product)
    }

    fun updateProduct(product: Product) {
        repository.updateProduct(product)
    }

    fun createSale(
        name:String,
        description:String,
        amount: Int,
        totalPrice:String
    ) {
        viewModelScope.launch {
            repository.uploadNewSale(name,description,amount,totalPrice)
        }
    }

}
