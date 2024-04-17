package com.example.storesoftware.ui.product.editDeleteProduct

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.storesoftware.databinding.ActivitySelectProductBinding
import com.example.storesoftware.domain.model.Product
import com.example.storesoftware.ui.product.HomeProduct.MainProductActivity
import com.example.storesoftware.ui.product.HomeProduct.adapter.SpacingDecorator
import com.example.storesoftware.ui.product.editDeleteProduct.adapterSelectProduct.SelectProductAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SelectProductActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySelectProductBinding
    private val selectProductViewModel: SelectProductViewModel by viewModels()
    private lateinit var selectProductsAdapter: SelectProductAdapter
    private var user: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectProductBinding.inflate(layoutInflater)
        setContentView(binding.root)
        user = intent.extras?.getString("userId")
        initUi(user)
    }

    private fun initUi(user: String?) {
        initListeners(user)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                selectProductViewModel.uiState.collect { state ->
                    binding.pb.isVisible = state.isLoading
                    renderAllProducts(state.products, user)
                }
            }
        }
    }

    private fun initListeners(user: String?) {
        binding.ivBack.setOnClickListener {
            val intent = Intent(this, MainProductActivity::class.java)
            user?.let { intent.putExtra("userId", it) }
            startActivity(intent)
            finish()
        }
    }

    private fun renderAllProducts(products: List<Product>, user: String?) {
        selectProductsAdapter = SelectProductAdapter(products, user)
        binding.rvEditProducts.apply {
            layoutManager = GridLayoutManager(context, 2)
            addItemDecoration(SpacingDecorator(16))
            adapter = selectProductsAdapter
        }
        selectProductsAdapter.updatedList(products)
    }
}
