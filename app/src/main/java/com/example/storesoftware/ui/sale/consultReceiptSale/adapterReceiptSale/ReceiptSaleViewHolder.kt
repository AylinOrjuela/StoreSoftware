package com.example.storesoftware.ui.sale.consultReceiptSale.adapterReceiptSale

import android.app.Activity
import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.storesoftware.databinding.ItemPurchaseReceiptBinding
import com.example.storesoftware.domain.model.Sale
import com.example.storesoftware.ui.sale.editDeleteSale.EditDeleteSaleActivity

class ReceiptSaleViewHolder(view: View): RecyclerView.ViewHolder(view){
    private val binding = ItemPurchaseReceiptBinding.bind(view)

    fun render(sale: Sale, user: String){
        val context = binding.cvReceipt.context

        binding.apply {
            tvPrice.text = sale.name
            tvDate.text = sale.date
        }

        binding.cvReceipt.setOnClickListener {
            val intent = Intent(context, EditDeleteSaleActivity::class.java)
            intent.putExtra("userId", user)
            intent.putExtra("saleKey", sale.id)
            context.startActivity(intent)
            (context as Activity).finish()
        }
    }
}