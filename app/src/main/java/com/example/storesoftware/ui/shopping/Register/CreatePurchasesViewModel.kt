package com.example.storesoftware.ui.shopping.Register

import androidx.lifecycle.ViewModel
import com.example.storesoftware.data.network.FirebaseDataBaseService
import com.example.storesoftware.domain.model.BuyReceipt
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreatePurchasesViewModel @Inject constructor(private val firebaseDataBaseService: FirebaseDataBaseService) :
    ViewModel() {
    fun registerPurchaseReceipt(receipt: BuyReceipt) {
        firebaseDataBaseService.createPurchaseReceipt(receipt)
    }
}