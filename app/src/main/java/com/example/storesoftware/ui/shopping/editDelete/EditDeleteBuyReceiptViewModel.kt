package com.example.storesoftware.ui.shopping.editDelete

import androidx.lifecycle.ViewModel
import com.example.storesoftware.data.network.FirebaseDataBaseService
import com.example.storesoftware.domain.model.BuyReceipt
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EditDeleteBuyReceiptViewModel @Inject constructor(private val firebaseDataBaseService: FirebaseDataBaseService) :
    ViewModel() {
    suspend fun getReceipt(receiptId: String): BuyReceipt? {
        return firebaseDataBaseService.getBuyReceiptById(receiptId)
    }

    fun updateBuyReceipt(receiptData: BuyReceipt){
        firebaseDataBaseService.updateBuyReceipt(receiptData)
    }

    fun deleteBuyReceipt(receiptData: BuyReceipt){
        firebaseDataBaseService.deleteBuyReceipt(receiptData)
    }
}