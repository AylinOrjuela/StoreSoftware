package com.example.storesoftware.ui.shopping.Consult

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.storesoftware.databinding.ActivityConsultPurchaseReceiptsBinding
import com.example.storesoftware.domain.model.BuyReceipt
import com.example.storesoftware.ui.home.MainActivity
import com.example.storesoftware.ui.shopping.Consult.Adapter.PurchaseReceiptsAdapter
import com.example.storesoftware.ui.shopping.Register.CreatePurchasesActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ConsultPurchaseReceiptsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityConsultPurchaseReceiptsBinding
    private val consultPurchaseReceiptViewModel: ConsultPurchaseReceiptViewModel by viewModels()
    private var buyReceiptList = mutableListOf<BuyReceipt>()
    private lateinit var adapter: PurchaseReceiptsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConsultPurchaseReceiptsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val user = intent.extras?.getString("userId")

        initUI(user.toString())
    }

    private fun initUI(user: String) {
        initListeners(user)

        lifecycleScope.launch {
            buyReceiptList = consultPurchaseReceiptViewModel.getBuyReceiptList().toMutableList()
            setUpRecyclerView(user)
        }
    }

    private fun initListeners(user: String) {
        binding.btnCreate.setOnClickListener {
            val intent = Intent(this, CreatePurchasesActivity::class.java)
            intent.putExtra("userId", user)
            startActivity(intent)
            finish()
        }
        binding.btnBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("userId", user)
            startActivity(intent)
            finish()
        }
    }

    private fun setUpRecyclerView(user: String) {
        adapter = PurchaseReceiptsAdapter(buyReceiptList, user)
        binding.rvReceipts.adapter = adapter
        binding.rvReceipts.layoutManager = LinearLayoutManager(this)
        adapter.notifyDataSetChanged()
    }

}