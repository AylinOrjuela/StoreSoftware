package com.example.storesoftware.ui.product.editDeleteProduct.adapterSelectProduct

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.storesoftware.R
import com.example.storesoftware.domain.model.Product

class SelectProductAdapter(private var products: List<Product> = emptyList(), val user: String?) :
    RecyclerView.Adapter<SelectProductViewHolder>() {

    fun updatedList(products: List<Product>) {
        this.products = products
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectProductViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return SelectProductViewHolder(view)
    }

    override fun getItemCount(): Int = products.size

    override fun onBindViewHolder(holder: SelectProductViewHolder, position: Int) {
        val item = products[position]
        val user = user
        if (user != null) {
            holder.render(item, user)
        }
    }

}