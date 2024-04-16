package com.example.storesoftware.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.storesoftware.databinding.ActivityMainBinding
import com.example.storesoftware.ui.shopping.Consult.ConsultPurchaseReceiptViewModel
import com.example.storesoftware.ui.shopping.Consult.ConsultPurchaseReceiptsActivity
import com.example.storesoftware.ui.shopping.Register.CreatePurchasesActivity
import com.example.storesoftware.ui.user.ConsultUserActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityMainBinding.inflate(layoutInflater)
        val user = intent.extras?.getString("userId")
        setContentView(binding.root)
        initUI(user.toString())
    }

    private fun initUI(user:String) {
        initListeners(user)
    }

    private fun initListeners(user:String) {
        binding.user.setOnClickListener {
            val intent = Intent(this, ConsultUserActivity::class.java)
            intent.putExtra("userId", user)
            startActivity(intent)
            finish()
        }
        binding.btnShopping.setOnClickListener {
            val intent = Intent(this, ConsultPurchaseReceiptsActivity::class.java)
            intent.putExtra("userId", user)
            startActivity(intent)
            finish()
        }
    }
}