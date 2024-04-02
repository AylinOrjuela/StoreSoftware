package com.example.storesoftware.ui.user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.storesoftware.databinding.ActivityRegisterUserBinding

class RegisterUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterUserBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityRegisterUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
    }

    private fun initUI() {
        initListeners()
    }

    private fun initListeners() {
        binding.btnRegister.setOnClickListener {
            val intent = Intent(this, LogInActivity::class.java)
            startActivity(intent)
            finish()
            if(checkEmptySlots()){
                
            }
        }
    }

    private fun checkEmptySlots(): Boolean {

        return !(binding.tieName.text.isNullOrEmpty() ||
                binding.tieLastName.text.isNullOrEmpty() ||
                binding.tieCC.text.isNullOrEmpty() ||
                binding.tieUsername.text.isNullOrEmpty() ||
                binding.tiePassword.text.isNullOrEmpty())
    }
}