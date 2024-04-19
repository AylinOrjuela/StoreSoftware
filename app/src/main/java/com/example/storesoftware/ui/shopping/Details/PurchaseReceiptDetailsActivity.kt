package com.example.storesoftware.ui.shopping.Details

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.storesoftware.R
import com.example.storesoftware.databinding.ActivityPurchaseReceiptDetailsBinding
import com.example.storesoftware.domain.model.BuyReceipt
import com.example.storesoftware.domain.model.User
import com.example.storesoftware.ui.shopping.editDelete.EditDeleteBuyReceiptActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PurchaseReceiptDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPurchaseReceiptDetailsBinding
    private val purchaseReceiptDetails: PurchaseReceiptDetailsViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPurchaseReceiptDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userId = intent.extras?.getString("userId")
        val receiptId = intent.extras?.getString("receiptId")

        initUI(userId.toString(), receiptId.toString())
    }

    private fun initUI(user: String, receipt: String) {
        initListeners(user, receipt)
        setReceiptData(receipt)
    }

    private fun setReceiptData(receipt: String) {
        lifecycleScope.launch {
            val receipt =
                purchaseReceiptDetails.getReceipt(receipt) ?: BuyReceipt("", "", "", "", "", "")
            val user = purchaseReceiptDetails.getUser(receipt.userId) ?: User()

            binding.tvUserName.text = user.firstName + " " + user.lastName
            binding.tvDate.text = receipt.date
            binding.tvPrice.text = receipt.amount
            binding.tvDescription.text = receipt.description
            binding.tvUnits.text = receipt.units
        }
    }

    private fun initListeners(user: String, receipt: String) {
        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.btnEdit.setOnClickListener {
            val intent = Intent(this, EditDeleteBuyReceiptActivity::class.java)
            intent.putExtra("userId", user)
            intent.putExtra("receiptId", receipt)
            startActivity(intent)
        }
    }
}