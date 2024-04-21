package com.example.storesoftware.ui.sale.consultReceiptSale.adapterReceiptSale

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.storesoftware.R
import com.example.storesoftware.domain.model.Sale

class ReceiptSaleAdapter(private var Listsale: List<Sale> = emptyList(), val user: String?): RecyclerView.Adapter<ReceiptSaleViewHolder>(){

    fun updatedList(Listsale: List<Sale>){
        this.Listsale = Listsale
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReceiptSaleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_purchase_receipt, parent, false)
        return ReceiptSaleViewHolder(view)
    }

    override fun getItemCount(): Int = Listsale.size

    override fun onBindViewHolder(holder: ReceiptSaleViewHolder, position: Int) {
        val item = Listsale[position]
        val user = user
        if (user!= null){
            holder.render(item,user)
        }
    }
}