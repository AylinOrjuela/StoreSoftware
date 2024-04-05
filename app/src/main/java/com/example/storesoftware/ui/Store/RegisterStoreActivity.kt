package com.example.storesoftware.ui.Store

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.example.storesoftware.databinding.ActivityRegisterStoreBinding
import com.example.storesoftware.ui.home.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterStoreActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterStoreBinding
    private val registerStoreViewModel: RegisterStoreViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityRegisterStoreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = intent.extras?.getString("userId")

        checkExistingStore(user.toString())
        initUI(user.toString())
    }
    private fun checkExistingStore(user:String){
        binding.btnRegisterStore.isEnabled = false
        lifecycleScope.launch {
            val store = registerStoreViewModel.onAlreadyRegisterStore()
            store?.let {
                if (it.name.isNotEmpty() && it.address.isNotEmpty()){
                    goToMain(user)
                }else{
                    binding.btnRegisterStore.isEnabled = true
                }
            } ?: run {
                binding.btnRegisterStore.isEnabled = true
            }
        }
    }

    private fun goToMain(user: String){
        val intent = Intent(this,MainActivity::class.java)
        intent.putExtra("userId", user)
        startActivity(intent)
        finish()
    }

    private fun initUI(user:String) {
        initListeners(user)
    }
    private fun initListeners(user:String) {

        binding.tieName.doOnTextChanged { text, _, _, _ ->
            registerStoreViewModel.onNameChanged(text)
        }
        binding.tieAddress.doOnTextChanged { text, _, _, _ ->
            registerStoreViewModel.onAddressChanged(text)
        }

        binding.btnRegisterStore.setOnClickListener {
            registerStoreViewModel.onRegisterStoreSelected()
            goToMain(user)
        }
    }
}