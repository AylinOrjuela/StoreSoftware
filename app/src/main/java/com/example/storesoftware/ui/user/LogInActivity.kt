package com.example.storesoftware.ui.user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.storesoftware.R
import com.example.storesoftware.databinding.ActivityLogInBinding
import com.example.storesoftware.domain.model.User
import com.example.storesoftware.ui.Store.RegisterStoreActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LogInActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLogInBinding
    private val logInViewModel: LogInViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityLogInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
    }

    private fun initUI() {
        initListeners()
    }

    private fun initListeners() {
        binding.btnLogin.setOnClickListener {
            if (checkEmptyFields()) {
                val username = binding.tieUsername.text.toString()
                val password = binding.tiePassword.text.toString()

                logInViewModel.loginUser(username, password) {
                    if (it.isNotEmpty() && it.isNotBlank()) {
                        val intent = Intent(this, RegisterStoreActivity::class.java)
                        intent.putExtra("userId", it)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        binding.btnRegisterUser.setOnClickListener {
            val intent = Intent(this, RegisterUserActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun checkEmptyFields(): Boolean {
        return binding.tieUsername.text.toString() != "" && binding.tiePassword.text.toString() != ""
    }
}