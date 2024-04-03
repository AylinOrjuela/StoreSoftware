package com.example.storesoftware.ui.user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.storesoftware.databinding.ActivityRegisterUserBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterUserBinding
    private val registerUserViewModel: RegisterUserViewModel by viewModels()
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
            if (checkEmptySlots()) {
                registerUser()
                goToLogin()
            }
        }
    }

    private fun goToLogin() {
        val intent = Intent(this, LogInActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun registerUser() {
        val firstName = binding.tieName.text.toString()
        val lastName = binding.tieLastName.text.toString()
        val cc = binding.tieCC.text.toString()
        val username = binding.tieUsername.text.toString()
        val password = binding.tiePassword.text.toString()
        var code = binding.tieCode.text.toString()

        if(code != "95876"){
            code = "11111"
        }

        registerUserViewModel.createUser(firstName, lastName, cc, username, password, code)
    }

    private fun checkEmptySlots(): Boolean {
        return !(binding.tieName.text.isNullOrEmpty() ||
                binding.tieLastName.text.isNullOrEmpty() ||
                binding.tieCC.text.isNullOrEmpty() ||
                binding.tieUsername.text.isNullOrEmpty() ||
                binding.tiePassword.text.isNullOrEmpty())
    }
}