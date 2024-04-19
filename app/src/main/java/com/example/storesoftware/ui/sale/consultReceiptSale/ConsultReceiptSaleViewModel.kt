package com.example.storesoftware.ui.sale.consultReceiptSale

import androidx.lifecycle.ViewModel
import com.example.storesoftware.data.network.FirebaseDataBaseService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ConsultReceiptSaleViewModel @Inject constructor(val repository: FirebaseDataBaseService): ViewModel() {

}