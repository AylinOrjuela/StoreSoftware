package com.example.storesoftware.ui.user

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.storesoftware.databinding.ActivityConsultUserBinding
import com.example.storesoftware.domain.model.User
import com.example.storesoftware.ui.home.MainActivity
import com.example.storesoftware.ui.user.editDeleteUser.EditDeleteUserActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ConsultUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityConsultUserBinding
    private val consultUserViewModel: ConsultUserViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityConsultUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val user = intent.extras?.getString("userId")
        initUI(user.toString())
    }

    private fun initUI(user: String) {
        initListeners(user)
        printUserInfo(user)
    }

    private fun initListeners(user:String) {
        binding.btnBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("userId",user)
            startActivity(intent)
            finish()
        }
        binding.btnEdit.setOnClickListener {
            val intent = Intent(this, EditDeleteUserActivity::class.java)
            intent.putExtra("userId",user)
            startActivity(intent)
            finish()
        }
    }

    private fun printUserInfo(user:String) {
        lifecycleScope.launch {
            val userData = consultUserViewModel.getUserById(user) ?: User(firstName = "Undefined")
            binding.firstName.setText(userData.firstName)
            binding.lastName.setText(userData.lastName)
            binding.cc.setText(userData.cc)
        }
    }

}