package com.example.storesoftware.ui.shopping.Register

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.storesoftware.databinding.ActivityCreatePurchasesBinding
import com.example.storesoftware.domain.model.BuyReceipt
import com.example.storesoftware.ui.home.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import java.util.Date

@AndroidEntryPoint
class CreatePurchasesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreatePurchasesBinding
    private val createPurchasesViewModel: CreatePurchasesViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreatePurchasesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val user = intent.extras?.getString("userId")

        initUI(user.toString())
    }

    private fun initUI(user: String) {
        initListeners(user)
    }

    private fun initListeners(user: String) {
        binding.btnCreatePurchaseReceipt.setOnClickListener {
            if (checkEmptySlots()) {
                createReceipt(user)
            }
        }
        binding.btnBack.setOnClickListener {
            goToMain(user)
        }
    }

    private fun createReceipt(user: String) {
        val receipt = BuyReceipt(
            generateId(),
            user,
            binding.tieDate.text.toString(),
            binding.tieDescription.text.toString(),
            binding.tieUnit.text.toString(),
            binding.tiePrice.text.toString()
        )

        createPurchasesViewModel.registerPurchaseReceipt(receipt)
        goToMain(user)
    }

    private fun goToMain(user: String) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("userId", user)
        startActivity(intent)
        finish()
    }

    private fun generateId(): String {
        return Date().time.toString()
    }

    private fun checkEmptySlots(): Boolean {
        var check = false

        if (
            binding.tieDate.text.isNullOrBlank() ||
            binding.tieDescription.text.isNullOrBlank() ||
            binding.tiePrice.text.isNullOrBlank() ||
            binding.tieUnit.text.isNullOrBlank()
        ) {
            Toast.makeText(this, "Debe llenar todos los campos", Toast.LENGTH_SHORT).show()
        } else {
            check = true
        }

        return check
    }
}