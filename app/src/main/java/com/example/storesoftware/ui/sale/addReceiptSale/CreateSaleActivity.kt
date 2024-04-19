package com.example.storesoftware.ui.sale.addReceiptSale

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.storesoftware.databinding.ActivityCreateSaleBinding
import com.example.storesoftware.domain.model.Product
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CreateSaleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateSaleBinding
    private val createSaleViewModel: CreateSaleViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateSaleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val user = intent.extras?.getString("userId")
        val product = intent.extras?.getString("productKey")
        initUI(user, product)
    }

    private fun initUI(user: String?, product: String?) {
        initListeners(user, product)
        setProductInfo(product)
    }

    private fun initListeners(user: String?, product: String?) {
        binding.ivBack.setOnClickListener {
            back(user)
        }
        binding.btnSale.setOnClickListener {
            stock(user, product)
        }
    }

    private fun setProductInfo(product: String?) {
        lifecycleScope.launch {
            val productData = product?.let { createSaleViewModel.getProductByid(it) }
            if (productData != null) {
                binding.apply {
                    etName.setText(productData.name)
                    etPrice.setText(productData.price)

                }
            }
        }
    }

    private suspend fun setTotal(product: String?): String {
        val productData = product?.let { createSaleViewModel.getProductByid(it) }
        if (productData != null) {
            val total = (productData.price.toDouble() * binding.etCantidad.text.toString()
                .toDouble()).toString()
            return total
        }
        return ""
    }


    private fun stock(user: String?, product: String?) {
        lifecycleScope.launch {
            try {
                val stockUpdated = updateProduct(product, user)
                if (stockUpdated) {
                RegisterSale(user,product)

                } else {
                    Toast.makeText(
                        this@CreateSaleActivity,
                        "Error: Stock insuficiente",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } catch (e: Exception) {
                Toast.makeText(
                    this@CreateSaleActivity,
                    "Error al actualizar el stock: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun RegisterSale(user: String?, product: String?) {
        val name = binding.etName.text.toString()
        val description = binding.etDescription.text.toString()
        val amount = binding.etCantidad.text.toString().toInt()

        lifecycleScope.launch {
            val totalPrice = setTotal(product)
            createSaleViewModel.createSale(name, description, amount, totalPrice)
            Toast.makeText(this@CreateSaleActivity, "Registro exitoso", Toast.LENGTH_SHORT).show()
            back(user)
        }

    }


    private suspend fun updateProduct(product: String?, user: String?): Boolean {
        val productData = product?.let { createSaleViewModel.getProductByid(it) }
        if (productData != null) {
            val newStock = productData.stock - binding.etCantidad.text.toString().toInt()
            if (newStock >= 0) {
                val updateProduct = Product(
                    productData.id,
                    productData.imageUrl,
                    productData.name,
                    productData.description,
                    productData.price,
                    newStock
                )
                createSaleViewModel.updateProduct(updateProduct)
                back(user)
                return true
            }
        }
        return false
    }

    private fun back(user: String?) {
        val intent = Intent(this, SelectProductSaleActivity::class.java)
        intent.putExtra("userId", user)
        startActivity(intent)
        finish()
    }

}