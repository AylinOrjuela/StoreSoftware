package com.example.storesoftware.ui.user

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.storesoftware.R
import com.example.storesoftware.databinding.ActivityConsultUserBinding
import com.example.storesoftware.domain.model.User

class ConsultUserActivity : AppCompatActivity() {

    private lateinit var binding:ActivityConsultUserBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityConsultUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()
    }

    private fun initUI() {
        initListeners()
    }

    private fun initListeners() {

    }
}