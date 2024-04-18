package com.example.storesoftware.ui.shopping.editDelete

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import com.example.storesoftware.R
import com.example.storesoftware.databinding.ActivityEditDeleteBuyReceiptBinding
import com.example.storesoftware.domain.model.BuyReceipt
import com.example.storesoftware.domain.model.User
import com.example.storesoftware.ui.home.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EditDeleteBuyReceiptActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditDeleteBuyReceiptBinding
    private val editDeleteBuyReceiptViewModel: EditDeleteBuyReceiptViewModel by viewModels()
    private lateinit var receiptData: BuyReceipt
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditDeleteBuyReceiptBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userId = intent.extras?.getString("userId")
        val receiptId = intent.extras?.getString("receiptId")

        initUI(userId.toString(), receiptId.toString())
    }

    private fun initUI(user: String, receipt: String) {
        setReceiptData(receipt)
        initListeners(user)
    }

    private fun initListeners(user: String) {
        binding.btnBack.setOnClickListener {
            finish()
        }
        binding.btnDelete.setOnClickListener {
            deleteBuyReceipt(user)
        }
        binding.btnSaveChanges.setOnClickListener {
            if (!checkEmptySlots()) {
                updateData(user)
            } else {
                Toast.makeText(this, "Debe llenar todos los campos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun deleteBuyReceipt(user: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Eliminar Recibo")
        builder.setMessage("¿Estás seguro de que quieres eliminar el recibo de forma permanente?")

        builder.setPositiveButton("Sí") { _, _ ->
            editDeleteBuyReceiptViewModel.deleteBuyReceipt(receiptData)
            Toast.makeText(
                this@EditDeleteBuyReceiptActivity,
                "Recibo Eliminado Exitosamente",
                Toast.LENGTH_SHORT
            ).show()
            backToMain(user)
        }
        builder.setNegativeButton("No") { _, _ -> }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.show()


    }

    private fun backToMain(user: String) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("userId", user)
        startActivity(intent)
        finish()
    }

    private fun updateData(user: String) {
        receiptData.date = binding.tieDate.text.toString()
        receiptData.units = binding.tieUnit.text.toString()
        receiptData.amount = binding.tiePrice.text.toString()
        receiptData.description = binding.tieDescription.text.toString()

        editDeleteBuyReceiptViewModel.updateBuyReceipt(receiptData)

        backToMain(user)
    }

    private fun checkEmptySlots(): Boolean {
        return (binding.tieDate.text.isNullOrEmpty()
                || binding.tieDescription.text.isNullOrEmpty()
                || binding.tieUnit.text.isNullOrEmpty()
                || binding.tiePrice.text.isNullOrEmpty())
    }

    private fun setReceiptData(receipt: String) {
        lifecycleScope.launch {
            receiptData =
                editDeleteBuyReceiptViewModel.getReceipt(receipt) ?: BuyReceipt(
                    "",
                    "",
                    "",
                    "",
                    "",
                    ""
                )

            binding.tieDate.setText(receiptData.date)
            binding.tieDescription.setText(receiptData.description)
            binding.tieUnit.setText(receiptData.units)
            binding.tiePrice.setText(receiptData.amount)

        }
    }

}