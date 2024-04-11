package com.example.storesoftware.ui.product.addproduct

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.example.storesoftware.databinding.ActivityCreateProductBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Objects

@AndroidEntryPoint
class CreateProductActivity : AppCompatActivity() {

    companion object {
        fun create(context: Context): Intent {
            return Intent(context, CreateProductActivity::class.java)
        }
    }

    private lateinit var uri: Uri

    lateinit var binding: ActivityCreateProductBinding
    private lateinit var createProductViewModel: CreateProductViewModel

    private val intentCameraLauncher =
        registerForActivityResult(ActivityResultContracts.TakePicture()) {
            if (it && uri.path?.isNotEmpty() == true) {
                createProductViewModel.onImageSelected(uri)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateProductBinding.inflate(layoutInflater)
        createProductViewModel = ViewModelProvider(this)[CreateProductViewModel::class.java]
        setContentView(binding.root)
        initUI()
    }

    private fun initUI() {
        initListeners()
        initUIState()
    }

    private fun initUIState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                createProductViewModel.uiState.collect {
                    binding.pb.isVisible = it.isLoading
                    binding.btnAddProduct.isEnabled = it.isValidProduct()
                    showImage(it.imageUrl)
                    if(it.error.isNullOrBlank()){

                    }
                }
            }
        }
    }

    private fun showImage(imageUrl: String) {
        val emptyImage = imageUrl.isEmpty()

        binding.apply {
            image.isVisible = emptyImage
            etImage.isVisible = emptyImage
            cvImageProduct.isVisible = !emptyImage
            Glide.with(this@CreateProductActivity).load(imageUrl).into(ivProduct)
        }
    }

    private fun initListeners() {
        binding.etName.doOnTextChanged { text, start, before, count ->
            createProductViewModel.onNameChanged(text)
        }
        binding.etDescription.doOnTextChanged { text, start, before, count ->
            createProductViewModel.onDescriptionChanged(text)
        }
        binding.etPrice.doOnTextChanged { text, start, before, count ->
            createProductViewModel.onPriceChanged(text)
        }
        binding.etStock.doOnTextChanged { text, start, before, count ->
            createProductViewModel.onStockChanged(text)
        }
        binding.etImage.setOnClickListener {
            takePhoto()
        }
        binding.btnAddProduct.setOnClickListener{
            createProductViewModel.onAddProductSelected{
                setResult(RESULT_OK)
                finish()
            }
        }
        binding.ivBack.setOnClickListener{
            setResult(RESULT_CANCELED)
            finish()
        }
    }

    private fun takePhoto() {
        generateUri()
        intentCameraLauncher.launch(uri)
    }

    private fun generateUri() {
        uri = FileProvider.getUriForFile(
            Objects.requireNonNull(this),
            "com.example.storesoftware.provider",
            createFile()
        )
    }

    private fun createFile(): File {
        val name: String = SimpleDateFormat("yyyyMMdd_hhmmss").format(Date()) + "image"
        return File.createTempFile(name, ".jpg", externalCacheDir)
    }
}