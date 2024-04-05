package com.example.storesoftware.ui.user.editDeleteUser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.storesoftware.R
import com.example.storesoftware.databinding.ActivityEditDeleteUserBinding
import com.example.storesoftware.ui.user.LogInViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditDeleteUserActivity : AppCompatActivity() {
    private lateinit var binding:ActivityEditDeleteUserBinding
    private val editDeleteUserViewModel: EditDeleteUserViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityEditDeleteUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
    }

    private fun initUI() {
        initListeners()
    }

    private fun initListeners() {

    }
}