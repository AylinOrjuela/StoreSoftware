package com.example.storesoftware.ui.product.HomeProduct

import android.app.Activity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.storesoftware.R
import com.example.storesoftware.databinding.ActivityMainProductBinding
import com.example.storesoftware.domain.model.Product
import com.example.storesoftware.ui.product.HomeProduct.adapter.ProductsAdapter
import com.example.storesoftware.ui.product.HomeProduct.adapter.SpacingDecorator
import com.example.storesoftware.ui.product.HomeProduct.adapter.TopProductsAdater
import com.example.storesoftware.ui.product.addproduct.CreateProductActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainProductActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainProductBinding
    private lateinit var mainProductViewModel: MainProductViewModel
    private lateinit var productsAdapter: ProductsAdapter
    private lateinit var topProductAdapter: TopProductsAdater

    private val createProductLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){result->
        if(result.resultCode == Activity.RESULT_OK){
            mainProductViewModel.getdata()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainProductBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mainProductViewModel = ViewModelProvider(this)[MainProductViewModel::class.java]
        initUI()
    }

    private fun initListeners() {
        binding.viewToolBar.tvAddProduct.setOnClickListener {
            createProductLauncher.launch(CreateProductActivity.create(this))
        }

    }

    private fun initUI() {
        initShimmer()
        initListeners()
        initList()
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainProductViewModel.uiState.collect { state ->
                    renderLastProduct(state.lastProduct)
                    renderTopProducts(state.topProducts)
                    renderAllProducts(state.products)
                }
            }
        }
    }

    private fun initShimmer(){
        binding.viewLastProductShimmer.root.startShimmer()
        binding.shimmerTopProducts.startShimmer()
    }

    private fun initList() {
        productsAdapter = ProductsAdapter()
        binding.rvProducts.apply {
            layoutManager = GridLayoutManager(context, 2)
            addItemDecoration(SpacingDecorator(16))
            adapter = productsAdapter
        }
        topProductAdapter = TopProductsAdater()
        binding.rvTopProducts.apply {
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL, false)
            adapter = topProductAdapter
        }
    }

    private fun renderAllProducts(products: List<Product>) {
        productsAdapter.updatedList(products)
    }

    private fun renderTopProducts(topProducts: List<Product>) {
        if(topProducts.isEmpty())return
        topProductAdapter.updatedList(topProducts)
        binding.shimmerTopProducts.isVisible = false
        binding.shimmerTopProducts.stopShimmer()
    }

    private fun renderLastProduct(lastProduct: Product?) {
        if (lastProduct == null) return
        binding.viewLastProduct.tvTitle.text = lastProduct.name
        binding.viewLastProduct.tvDescription.text = lastProduct.description
        binding.viewLastProduct.tvPrice.text = lastProduct.price
        Glide.with(this).load(lastProduct.imageUrl).placeholder(R.drawable.ic_placeholder)
            .into(binding.viewLastProduct.ivLastproduct)
        binding.viewLastProduct.root.isVisible = true
        binding.viewLastProductShimmer.root.stopShimmer()
    }
}