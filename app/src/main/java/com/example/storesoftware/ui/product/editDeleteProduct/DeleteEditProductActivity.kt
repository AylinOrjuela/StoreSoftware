package com.example.storesoftware.ui.product.editDeleteProduct

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.storesoftware.databinding.ActivityDeleteEditProductBinding
import com.example.storesoftware.domain.model.Product
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DeleteEditProductActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDeleteEditProductBinding
    private val deleteEditProductViewModel: DeleteEditProductViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeleteEditProductBinding.inflate(layoutInflater)
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
        binding.btnDeleteProduct.setOnClickListener {
            if (product != null) {
                deleteProduct(product, user)
            }
        }
        binding.btnEditProduct.setOnClickListener {
            if (checkEmptyFields()) {
                updateProduct(product, user)
                Toast.makeText(this, "Producto Actualizado", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Debe rellenar todos los campos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setProductInfo(product: String?) {
        lifecycleScope.launch {
            val productData = product?.let { deleteEditProductViewModel.getProductByid(it) }
            if (productData != null) {
                binding.apply {
                    etName.setText(productData.name)
                    etDescription.setText(productData.description)
                    etStock.setText(productData.stock.toString())
                    etPrice.setText(productData.price)
                }
            }
        }
    }

    private fun updateProduct(product: String?, user: String?) {
        lifecycleScope.launch {
            val productData = product?.let { deleteEditProductViewModel.getProductByid(product) }
            val stockText = binding.etStock.text.toString()
            val isStockValid = stockText.isNotBlank() && stockText.toIntOrNull() != null

            if (productData != null && isStockValid) {
                val updateProduct = Product(
                    productData.id,
                    productData.imageUrl,
                    binding.etName.text.toString(),
                    binding.etDescription.text.toString(),
                    binding.etPrice.text.toString(),
                    stockText.toInt()
                )
                deleteEditProductViewModel.updateProduct(updateProduct)
                back(user)
            }
        }
    }

    private fun deleteProduct(product: String, user: String?) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Eliminar Producto")
        builder.setMessage("¿Está seguro de eliminar el producto?")

        builder.setPositiveButton("Sí") { _, _ ->
            deleteEditProductViewModel.deleteProduct(product)
            back(user)

            Toast.makeText(
                this@DeleteEditProductActivity,
                "Producto eliminado Exitosamente",
                Toast.LENGTH_SHORT
            ).show()
        }
        builder.setNegativeButton("No") { _, _ -> }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.show()
    }


    private fun back(user: String?) {
        val intent = Intent(this, SelectProductActivity::class.java)
        intent.putExtra("userId", user)
        startActivity(intent)
        finish()
    }


    private fun checkEmptyFields(): Boolean {
        return binding.etName.text.toString() != "" && binding.etDescription.text.toString() != ""
                && binding.etPrice.text.toString() != ""
    }


}