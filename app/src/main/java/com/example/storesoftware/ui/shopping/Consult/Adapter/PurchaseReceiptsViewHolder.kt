package com.example.storesoftware.ui.shopping.Consult.Adapter

import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.storesoftware.databinding.ItemPurchaseReceiptBinding
import com.example.storesoftware.domain.model.BuyReceipt
import com.example.storesoftware.ui.shopping.Details.PurchaseReceiptDetailsActivity

class PurchaseReceiptsViewHolder(view: View) : ViewHolder(view) {

    private val binding = ItemPurchaseReceiptBinding.bind(view)
    fun bind(receipt: BuyReceipt, user: String) {
        binding.tvDate.text = receipt.date
        binding.tvPrice.text = receipt.idReceipt

        val context = binding.cvReceipt.context

        binding.cvReceipt.setOnClickListener {
            val intent = Intent(context,PurchaseReceiptDetailsActivity::class.java)
            intent.putExtra("userId",user)
            intent.putExtra("receiptId",receipt.idReceipt)
            context.startActivity(intent)
        }

    }
}