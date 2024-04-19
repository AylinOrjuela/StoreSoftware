package com.example.storesoftware.ui.sale.addReceiptSale

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.storesoftware.databinding.ActivitySelectProductSaleBinding
import com.example.storesoftware.domain.model.Product
import com.example.storesoftware.ui.product.HomeProduct.adapter.SpacingDecorator
import com.example.storesoftware.ui.sale.addReceiptSale.adapterProductSale.ProductSaleAdapter
import com.example.storesoftware.ui.sale.consultReceiptSale.ConsultReceiptSaleActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SelectProductSaleActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySelectProductSaleBinding
    private val selectProductSaleViewModel: SelectProductSaleViewModel by viewModels()
    private lateinit var productSaleAdapter: ProductSaleAdapter
    private var user: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectProductSaleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        user = intent.extras?.getString("userId")
        initUi(user)
    }

    private fun initUi(user: String?) {
        initListeners(user)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                selectProductSaleViewModel.uiState.collect{
                    renderAllproducts(it.products, user)
                }
            }
        }
    }

    private fun initListeners(user: String?) {
        binding.ivBack.setOnClickListener {
            val intent = Intent(this, ConsultReceiptSaleActivity::class.java)
            intent.putExtra("userId", user)
            startActivity(intent)
            finish()
        }
    }

    private fun renderAllproducts(products:List<Product>, user: String?) {
        productSaleAdapter = ProductSaleAdapter(products, user)
        binding.rvProducts.apply {
            layoutManager = GridLayoutManager(context, 2)
            addItemDecoration(SpacingDecorator(10))
            adapter = productSaleAdapter
        }
        productSaleAdapter.updatedList(products)
    }

}