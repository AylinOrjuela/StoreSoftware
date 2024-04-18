package com.example.storesoftware.ui.shopping.Details

import androidx.lifecycle.ViewModel
import com.example.storesoftware.data.network.FirebaseDataBaseService
import com.example.storesoftware.domain.model.BuyReceipt
import com.example.storesoftware.domain.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PurchaseReceiptDetailsViewModel @Inject constructor(private val firebaseDataBaseService: FirebaseDataBaseService) :
    ViewModel() {

        suspend fun getUser(userId:String): User? {
            return firebaseDataBaseService.getUserById(userId)
        }

        suspend fun getReceipt(receiptId:String):BuyReceipt? {
            return firebaseDataBaseService.getBuyReceiptById(receiptId)
        }

}