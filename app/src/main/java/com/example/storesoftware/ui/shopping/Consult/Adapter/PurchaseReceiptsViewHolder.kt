package com.example.storesoftware.ui.shopping.Consult.Adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.storesoftware.databinding.ItemPurchaseReceiptBinding
import com.example.storesoftware.domain.model.BuyReceipt

class PurchaseReceiptsViewHolder(view: View) : ViewHolder(view) {

    private val binding = ItemPurchaseReceiptBinding.bind(view)
    fun bind(receipt: BuyReceipt, user: String) {
        binding.tvDate.text = receipt.date
        binding.tvPrice.text = receipt.amount

        binding.cvReceipt.setOnClickListener {
            //Go to Consult Screen
            //Send receipt object to avoid data consumption
            //Send user ID, because we need to maintain the userID data
        }

    }
}