package com.example.storesoftware.ui.user.editDeleteUser

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.storesoftware.databinding.ActivityEditDeleteUserBinding
import com.example.storesoftware.databinding.ActivityMainBinding
import com.example.storesoftware.domain.model.User
import com.example.storesoftware.ui.home.MainActivity
import com.example.storesoftware.ui.user.LogInActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EditDeleteUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditDeleteUserBinding
    private val editDeleteUserViewModel: EditDeleteUserViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityEditDeleteUserBinding.inflate(layoutInflater)
        val user = intent.extras?.getString("userId")
        val code = intent.extras?.getString("code")
        setContentView(binding.root)
        initUI(user.toString(), code.toString())
    }

    private fun initUI(user: String, code: String) {
        initListeners(user, code)
        setUserInfo(user)
    }

    private fun initListeners(user: String, code: String) {
        binding.btnBack.setOnClickListener {
            goToMainScreen(user)
        }
        binding.btnUpdate.setOnClickListener {
            if (checkEmptyFields()) {
                updateUserData(user, code)
                Toast.makeText(this, "Usuario Actualizado", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Debe rellenar todos los campos", Toast.LENGTH_SHORT).show()
            }
        }
        binding.btnDelete.setOnClickListener {
            deleteUser(user)
        }
    }

    private fun deleteUser(userId:String){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Eliminar Usuario")
        builder.setMessage("¿Estás seguro de que quieres eliminar tu usuario de forma permanente?")

        builder.setPositiveButton("Sí") { _, _ ->
            editDeleteUserViewModel.deleteUser(userId)
            goToLogInScreen()

            Toast.makeText(
                this@EditDeleteUserActivity,
                "Usuario Eliminado Exitosamente",
                Toast.LENGTH_SHORT
            ).show()
        }
        builder.setNegativeButton("No") { _, _ -> }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.show()
    }

    private fun goToLogInScreen(){
        val intent = Intent(this, LogInActivity::class.java)
        startActivity(intent)
        finish()
    }
    private fun goToMainScreen(user: String) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("userId", user)
        startActivity(intent)
        finish()
    }

    private fun updateUserData(userId: String, code: String) {
        val updateUser = User(
            id = userId,
            firstName = binding.tieName.text.toString(),
            lastName = binding.tieLastName.text.toString(),
            cc = binding.tieCC.text.toString(),
            username = binding.tieUsername.text.toString(),
            password = binding.tiePassword.text.toString(),
            code = code
        )
        editDeleteUserViewModel.updateUser(updateUser)
        goToMainScreen(userId)
    }

    private fun setUserInfo(userId: String) {
        lifecycleScope.launch {
            val userData =
                editDeleteUserViewModel.getUserById(userId) ?: User(firstName = "Undefined")
            binding.tieName.setText(userData.firstName)
            binding.tieLastName.setText(userData.lastName)
            binding.tieCC.setText(userData.cc)
            binding.tieUsername.setText(userData.username)
            binding.tiePassword.setText(userData.password)
        }
    }

    private fun checkEmptyFields(): Boolean {
        return binding.tieUsername.text.toString() != "" && binding.tiePassword.text.toString() != ""
    }
}