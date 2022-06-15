package com.dicoding.picodiploma.submissionaplikasistoryapp.view.camera

import android.Manifest
import android.content.Intent
import android.content.Intent.ACTION_GET_CONTENT
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.dicoding.picodiploma.submissionaplikasistoryapp.R
import com.dicoding.picodiploma.submissionaplikasistoryapp.Utils.createTempFile
import com.dicoding.picodiploma.submissionaplikasistoryapp.Utils.reduceFileImage
import com.dicoding.picodiploma.submissionaplikasistoryapp.Utils.rotateBitmap
import com.dicoding.picodiploma.submissionaplikasistoryapp.Utils.uriToFile
import com.dicoding.picodiploma.submissionaplikasistoryapp.databinding.ActivityCameraBinding
import com.dicoding.picodiploma.submissionaplikasistoryapp.view.home.HomeActivity
import com.dicoding.picodiploma.submissionaplikasistoryapp.view.welcome.ViewModelFactory
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class CameraActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCameraBinding
    private lateinit var factory: ViewModelFactory
    private lateinit var currentPhotoPath: String
    private var getFile: File? = null
    private val cameraViewModel: CameraViewModel by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupView()
        setupViewModel()
        setPermission()
        setupAction()
    }

    //Camera Permission
    companion object {
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    }
    
    private fun setupAction() {
    binding.apply {
        btnCamera.setOnClickListener { startTakePhoto() }
        btnGalery.setOnClickListener { startTakeGalery() }
        btnUpload.setOnClickListener { uploadStory() }
    }
}

    private fun setPermission() {
        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                this@CameraActivity,
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }
    }

    private fun setupViewModel() {
        factory = ViewModelFactory.getInstance(this)
    }

    private fun setupView() {
        binding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.apply {
            title = getString(R.string.tambah_story)
            setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK) {
            val myFile = File(currentPhotoPath)
            getFile = myFile

            val result = rotateBitmap(
                BitmapFactory.decodeFile(getFile?.path),
                true
            )
            binding.imgCamera.setImageBitmap(result)
        }
    }

    private fun startTakePhoto() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.resolveActivity(packageManager)

        createTempFile(application).also {
            val photoURI: Uri = FileProvider.getUriForFile(
                this@CameraActivity,
                "com.dicoding.picodiploma.submissionaplikasistoryapp",
                it
            )
            currentPhotoPath = it.absolutePath

            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            launcherIntentCamera.launch(intent)
        }
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg: Uri = result.data?.data as Uri
            val myFile = uriToFile(selectedImg, this)

            getFile = myFile
            binding.imgCamera.setImageURI(selectedImg)
        }
    }
    private fun startTakeGalery() {
        val intent = Intent()
        intent.action = ACTION_GET_CONTENT
        intent.type = "image/*"

        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }

    private fun uploadStory() {
        showLoading()
        cameraViewModel.getSession().observe(this@CameraActivity) {
            if (getFile != null) {
                val file = reduceFileImage(getFile as File)
                val requestImageFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
                val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                    "photo",
                    file.name,
                    requestImageFile
                )
                uploadResponse(
                    it.token,
                    imageMultipart,
                    binding.txtEdttxtcamera.text.toString().toRequestBody("text/plain".toMediaType())
                )
            } else {
                Toast.makeText(
                    this@CameraActivity,
                    getString(R.string.upload),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun uploadResponse(
        token: String,
        file: MultipartBody.Part,
        description: RequestBody
    ) {
        cameraViewModel.storyUpload(token, file, description)
        cameraViewModel.responeUpload.observe(this@CameraActivity) {
            if (!it.error) {
                moveActivity()
            }
        }
        showToast()
    }

    private fun showLoading() {
        cameraViewModel.isLoading.observe(this@CameraActivity) {
            binding.pbCamera.visibility = if (it) View.VISIBLE else View.GONE
        }
    }

    private fun showToast() {
        cameraViewModel.toastText.observe(this@CameraActivity) {
            it.getContentIfNotHandled()?.let { toastText ->
                Toast.makeText(
                    this@CameraActivity, toastText, Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun moveActivity() {
        startActivity(Intent(this@CameraActivity, HomeActivity::class.java))
        finish()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

}