package com.rehat.rehatcoffee.presentation.admin.create

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.rehat.rehatcoffee.R
import com.rehat.rehatcoffee.core.RealPathUtil
import com.rehat.rehatcoffee.data.common.utils.WrappedResponse
import com.rehat.rehatcoffee.data.product.remote.dto.ProductResponse
import com.rehat.rehatcoffee.databinding.ActivityEditProductBinding
import com.rehat.rehatcoffee.domain.product.entity.KindsEntity
import com.rehat.rehatcoffee.domain.product.entity.ProductEntity
import com.rehat.rehatcoffee.presentation.admin.create.adapter.ProductKindsAdapter
import com.rehat.rehatcoffee.presentation.admin.dashboard.AdminDashboardActivity
import com.rehat.rehatcoffee.presentation.common.extention.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

@AndroidEntryPoint
class EditProductActivity : AppCompatActivity() {
    private lateinit var binding : ActivityEditProductBinding
    private val viewModel : CreateMenuViewModel by viewModels()
    private var selectedImageFile: File = File("")
    private var dataList = ArrayList<KindsEntity>()
    private lateinit var product : ProductEntity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        product = intent.parcelable<ProductEntity>(EDIT_PRODUCT)!!

        binding.apply {
           inputNamaProduct.setText(product.productName)
           inputHargaProduct.setText(product.price.toString())
           inputDeskripsi.setText(product.description)
        }

        Log.d("PRODYUCFD", product.toString())


        observe()
        setupAdapterKinds()
        setupListKinds()
        sendProduct()
        initListener()

        binding.btnImages.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK).apply {
                type = "image/*"
            }
            startActivityForResult(intent, PICK_IMAGE_REQUEST)
        }
    }

    private fun initListener(){
        binding.apply {
            toolbar.setOnClickListener {
                finish()
            }
        }
    }

    private fun setupAdapterKinds(){
        val filterAdapter = ProductKindsAdapter(dataList)
        filterAdapter.setItemClickListener(object : ProductKindsAdapter.OnItemClickListener{
            override fun onClick(kinds : KindsEntity) {
                binding.tvKinds.text = kinds.name
            }
        })
        binding.rvKinds.apply {
            layoutManager = LinearLayoutManager(this@EditProductActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = filterAdapter
        }
    }

    private fun observe(){
        viewModel.mState
            .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach { state ->
                handleStateChange(state)
            }
            .launchIn(lifecycleScope)
    }

    private fun handleStateChange(state: CreateProductViewState){
        when(state){
            is CreateProductViewState.Init -> Unit
            is CreateProductViewState.ErrorUpload -> handleErrorUpload(state.rawResponse)
            is CreateProductViewState.SuccessCreate -> handleSuccess()
            is CreateProductViewState.ShowToast -> showToast(state.message)
            is CreateProductViewState.IsLoading -> handleLoading(state.isLoading)
        }
    }

    private fun handleErrorUpload(response: WrappedResponse<ProductResponse>){
        handleLoading(false)
        showGenericAlertDialog(response.message)
    }

    private fun handleSuccess(){
        movePage()
    }

    private fun movePage(){
        val intent = Intent(this, AdminDashboardActivity::class.java)
        startActivity(intent)
        finishAffinity()
    }

    private fun handleLoading(isLoading: Boolean){
        if(isLoading){
            binding.loadingProgressBar.visible()
        }else{
            binding.loadingProgressBar.gone()
        }
    }

    private fun sendProduct() {
        binding.fabSend.setOnClickListener {
            with(binding) {
                val inputNamaProduct = inputNamaProduct.text.toString().trim()
                val inputHargaProduct = inputHargaProduct.text.toString().trim()
                val inputDeskripsi = inputDeskripsi.text.toString().trim()
                val tvKinds = tvKinds.text.toString().trim()

                if (isValidInput(inputNamaProduct, inputHargaProduct, inputDeskripsi, tvKinds)) {
                    val productParams = mutableMapOf<String, RequestBody>().apply {
                        put("product_name", inputNamaProduct.toRequestBody())
                        put("price", inputHargaProduct.toRequestBody())
                        put("description", inputDeskripsi.toRequestBody())
                        put("kinds", tvKinds.toRequestBody())
                    }
                    if (selectedImageFile.exists()) {
                        val requestBody = selectedImageFile.asRequestBody("image/jpeg".toMediaTypeOrNull())
                        val partFile1 = MultipartBody.Part.createFormData("images", selectedImageFile.name, requestBody)

                        runCatching {
                            Log.d("PRODYUCFD", product.toString())
                            product.id?.let { it1 -> viewModel.updateProduct(it1,productParams, partFile1) }
                        }.onSuccess {
                            showToast("Berhasil")
                        }.onFailure {
                            showToast(it.message ?: "Error occurred")
                        }
                    } else {
                        showToast("File not found")
                    }
                } else {
                    showToast("Please fill in all the required fields")
                }
            }
        }
    }

    private fun isValidInput(inputNamaProduct: String, inputHargaProduct: String, inputDeskripsi: String, tvKinds: String): Boolean {
        return inputNamaProduct.isNotEmpty() && inputHargaProduct.isNotEmpty() && inputDeskripsi.isNotEmpty() && tvKinds.isNotEmpty()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.data != null) {
            val imageUri = data.data
            Glide.with(this)
                .load(imageUri)
                .apply(RequestOptions.centerCropTransform())
                .into(binding.imagePrduct)
            selectedImageFile = File(getRealPathFromUri(this, imageUri!!))
            val inputStream = contentResolver.openInputStream(imageUri)!!
            val bitmap = BitmapFactory.decodeStream(inputStream)
            inputStream.close()
            val compressedBitmap = compressImage(bitmap)
            selectedImageFile = saveBitmapToFile(compressedBitmap)
        }
    }

    private fun getRealPathFromUri(context: Context, uri: Uri): String {
        return RealPathUtil.getRealPath(context, uri).toString()
    }

    private fun compressImage(image: Bitmap): Bitmap {
        val outputStream = ByteArrayOutputStream()
        var quality = 100
        do {
            outputStream.reset()
            image.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
            quality -= 10
        } while (outputStream.toByteArray().size / 1024 > 100 && quality > 0)
        val result = BitmapFactory.decodeByteArray(outputStream.toByteArray(), 0, outputStream.toByteArray().size)
        outputStream.close()
        return result
    }


    private fun saveBitmapToFile(bitmap: Bitmap): File {
        val maxSize = 100 // maximum size in kilobytes
        var compression = 90 // initial compression quality
        val file = File(cacheDir, "compressed_image.jpg")
        file.createNewFile()

        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, compression, outputStream)
        while (outputStream.toByteArray().size / 1024 > maxSize && compression > 10) {
            compression -= 10
            outputStream.reset()
            bitmap.compress(Bitmap.CompressFormat.JPEG, compression, outputStream)
        }

        val fos = FileOutputStream(file)
        fos.write(outputStream.toByteArray())
        fos.flush()
        fos.close()

        return file
    }


    private fun setupListKinds(){
        dataList.add(
            KindsEntity(
                "food"
            )
        )
        dataList.add(
            KindsEntity(
                "drink"
            )
        )
    }

    companion object{
        private const val PICK_IMAGE_REQUEST = 1
        const val EDIT_PRODUCT = "EDIT"
    }
}