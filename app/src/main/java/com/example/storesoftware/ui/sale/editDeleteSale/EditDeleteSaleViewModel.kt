package com.example.storesoftware.ui.sale.editDeleteSale

import androidx.lifecycle.ViewModel
import com.example.storesoftware.data.network.FirebaseDataBaseService
import com.example.storesoftware.domain.model.Sale
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class EditDeleteSaleViewModel @Inject constructor(private val repository: FirebaseDataBaseService) :
    ViewModel() {
    suspend fun getReceiptSaleById(sale: String): Sale? {
        return repository.getSaleReceiptById(sale)
    }

    fun updateReceiptSale(sale: Sale){
        repository.updateSaleReceipt(sale)
    }

    fun deleteReceiptSale(sale: String?){
        repository.deleteSaleReceipt(sale)
    }

}