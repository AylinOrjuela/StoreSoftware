package com.example.storesoftware.ui.shopping.Consult

import androidx.lifecycle.ViewModel
import com.example.storesoftware.data.network.FirebaseDataBaseService
import com.example.storesoftware.domain.model.BuyReceipt
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ConsultPurchaseReceiptViewModel @Inject constructor(private val firebaseDataBaseService: FirebaseDataBaseService) :
    ViewModel() {
    suspend fun getBuyReceiptList(): List<BuyReceipt> {
        return firebaseDataBaseService.getListBuyReceipts()
    }


}