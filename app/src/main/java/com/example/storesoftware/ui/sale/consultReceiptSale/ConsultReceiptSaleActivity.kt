package com.example.storesoftware.ui.sale.consultReceiptSale

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.storesoftware.databinding.ActivityConsultReceiptSaleBinding
import com.example.storesoftware.domain.model.Sale
import com.example.storesoftware.ui.home.MainActivity
import com.example.storesoftware.ui.sale.addReceiptSale.SelectProductSaleActivity
import com.example.storesoftware.ui.sale.consultReceiptSale.adapterReceiptSale.ReceiptSaleAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ConsultReceiptSaleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityConsultReceiptSaleBinding
    private val consultReceiptSaleViewModel: ConsultReceiptSaleViewModel by viewModels()
    private lateinit var receiptSaleAdapter: ReceiptSaleAdapter
    private var user: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConsultReceiptSaleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        user = intent.extras?.getString("userId")
        initUI(user)
    }

    private fun initUI(user: String?) {
        initListeners(user)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                consultReceiptSaleViewModel.uiState.collect { state ->
                    renderAllReceiptSale(state.listSale, user)
                }
            }
        }
    }

    private fun initListeners(user: String?) {
        binding.btnAddReceiptSale.setOnClickListener {
            val intent = Intent(this, SelectProductSaleActivity::class.java)
            intent.putExtra("userId", user)
            startActivity(intent)
            finish()
        }
        binding.ivBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("userId", user)
            startActivity(intent)
            finish()
        }
    }

    private fun renderAllReceiptSale(listSale: List<Sale>, user: String?) {
        receiptSaleAdapter = ReceiptSaleAdapter(listSale, user)
        binding.rvReceiptSale.adapter = receiptSaleAdapter
        binding.rvReceiptSale.layoutManager = LinearLayoutManager(this)
        receiptSaleAdapter.notifyDataSetChanged()
    }
}