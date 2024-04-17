package com.example.storesoftware.ui.product.editDeleteProduct

import androidx.lifecycle.ViewModel
import com.example.storesoftware.data.network.FirebaseDataBaseService
import com.example.storesoftware.domain.model.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DeleteEditProductViewModel @Inject constructor(private val firebaseDataBaseService: FirebaseDataBaseService): ViewModel() {
    suspend fun getProductByid(product: String): Product?{
        return firebaseDataBaseService.getProductById(product)
    }

    fun updateProduct(product: Product){
        firebaseDataBaseService.updateProduct(product)
    }

    fun deleteProduct(product: String){
        firebaseDataBaseService.deleteProduct(product)
    }
}