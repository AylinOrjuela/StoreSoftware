package com.example.storesoftware.ui.shopping.Consult.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.storesoftware.R
import com.example.storesoftware.domain.model.BuyReceipt

class PurchaseReceiptsAdapter(private val purchaseReceiptList: List<BuyReceipt>,private val user:String) :
    RecyclerView.Adapter<PurchaseReceiptsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PurchaseReceiptsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return PurchaseReceiptsViewHolder(
            layoutInflater.inflate(
                R.layout.item_purchase_receipt,
                parent,
                false
            )
        )
    }

    override fun getItemCount() = purchaseReceiptList.size

    override fun onBindViewHolder(holder: PurchaseReceiptsViewHolder, position: Int) {
        val item = purchaseReceiptList[position]
        holder.bind(item, user)
    }
}