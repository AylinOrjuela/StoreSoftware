package com.example.storesoftware.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.storesoftware.databinding.ActivityMainBinding
<<<<<<< HEAD
import com.example.storesoftware.ui.shopping.Consult.ConsultPurchaseReceiptViewModel
import com.example.storesoftware.ui.shopping.Consult.ConsultPurchaseReceiptsActivity
import com.example.storesoftware.ui.shopping.Register.CreatePurchasesActivity
=======
import com.example.storesoftware.ui.product.HomeProduct.MainProductActivity
>>>>>>> d6c110ea9524d3bd1a459357f5696d45cd938b9c
import com.example.storesoftware.ui.user.ConsultUserActivity
import com.example.storesoftware.ui.user.LogInActivity
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
<<<<<<< HEAD
        binding.btnShopping.setOnClickListener {
            val intent = Intent(this, ConsultPurchaseReceiptsActivity::class.java)
=======
        binding.btnProduct.setOnClickListener {
            val intent = Intent(this, MainProductActivity::class.java)
>>>>>>> d6c110ea9524d3bd1a459357f5696d45cd938b9c
            intent.putExtra("userId", user)
            startActivity(intent)
            finish()
        }
<<<<<<< HEAD
=======
        binding.ivBack.setOnClickListener{
            val intent = Intent(this, LogInActivity::class.java)
            startActivity(intent)
            finish()
        }
>>>>>>> d6c110ea9524d3bd1a459357f5696d45cd938b9c
    }
}