package com.example.storesoftware.ui.user

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.storesoftware.R

class LogInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_log_in)
    }
}