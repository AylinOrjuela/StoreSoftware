package com.example.storesoftware.ui.sale.editDeleteSale

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import com.example.storesoftware.R
import com.example.storesoftware.databinding.ActivityEditDeleteSaleBinding
import com.example.storesoftware.domain.model.Sale
import com.example.storesoftware.ui.sale.consultReceiptSale.ConsultReceiptSaleActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint

class EditDeleteSaleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditDeleteSaleBinding
    private val editDeleteSaleViewModel : EditDeleteSaleViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditDeleteSaleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val user = intent.extras?.getString("userId")
        val receiptSale = intent.extras?.getString("saleKey")
        initUI(user, receiptSale)
    }

    private fun initUI(user: String?, receiptSale: String?) {
        initListeners(user, receiptSale)
        setProductInfo(receiptSale)

    }

    private fun initListeners(user: String?, receiptSale: String?) {
        binding.ivBack.setOnClickListener {
            back(user)
        }
        binding.btnDelete.setOnClickListener {
            if (receiptSale != null) {
                deleteSale(receiptSale, user)
            }
        }
        binding.btnEdit.setOnClickListener {
            if (checkEmptyFields()) {
                updateSale(receiptSale, user)
                Toast.makeText(this, "Producto Actualizado", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Debe rellenar todos los campos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateSale(receiptSale: String?, user: String?) {
        lifecycleScope.launch {
            val saleData = receiptSale?.let {
                editDeleteSaleViewModel.getReceiptSaleById(it)
            }
            if(saleData != null){
                val updateData = Sale (
                    saleData.id,
                    binding.etName.text.toString(),
                    binding.etDate.text.toString(),
                    binding.etDescription.text.toString(),
                    binding.etCantidad.text.toString().toInt(),
                    binding.etPrice.text.toString()
                )
                editDeleteSaleViewModel.updateReceiptSale(updateData)
                back(user)
            }
        }
    }

    private fun deleteSale(receiptSale: String, user: String?) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Eliminar recibo de venta")
        builder.setMessage("¿Está seguro de eliminar el recibo de venta?")

        builder.setPositiveButton("Sí") { _, _ ->
            editDeleteSaleViewModel.deleteReceiptSale(receiptSale)
            back(user)

            Toast.makeText(
                this@EditDeleteSaleActivity,
                "Recibo eliminado Exitosamente",
                Toast.LENGTH_SHORT
            ).show()
        }
        builder.setNegativeButton("No") { _, _ -> }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.show()
    }


    private fun setProductInfo(receiptSale: String?) {
        lifecycleScope.launch {
            val saleData = receiptSale?.let {
                editDeleteSaleViewModel.getReceiptSaleById(it)
            }
            if (saleData != null){
                binding.apply {
                    etName.setText(saleData.name)
                    etDescription.setText(saleData.description)
                    etCantidad.setText(saleData.amount.toString())
                    etPrice.setText(saleData.totalPrice)
                    etDate.setText(saleData.date)
                }
            }
        }
    }

    private fun checkEmptyFields(): Boolean {
        return binding.etName.text.toString() != "" && binding.etDescription.text.toString() != ""
                && binding.etPrice.text.toString() != ""  && binding.etName.text.toString() != ""
                && binding.etCantidad.text.toString() != ""  && binding.etDate.text.toString() != ""
    }

    private fun back(user: String?) {
        val intent = Intent(this, ConsultReceiptSaleActivity::class.java)
        intent.putExtra("userId", user)
        startActivity(intent)
        finish()
    }
}