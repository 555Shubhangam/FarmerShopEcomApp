package com.farmershop.ui.activity

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.farmershop.R
import com.farmershop.data.viewModel.ProfileViewModal
import com.farmershop.data.viewModel.ResetPasswordViewModal
import com.farmershop.databinding.ActivityResetPasswordBinding
import com.farmershop.ui.base.BaseActivityUser
import com.farmershop.databinding.ProfileBinding
import com.farmershop.ui.activity.auth.Login
import com.farmershop.utils.Constants
import com.farmershop.utils.ProgressDialog
import com.farmershop.utils.Resource
import kotlinx.android.synthetic.main.include_toolbar.*
import kotlinx.android.synthetic.main.profile.*
import java.io.File
import java.io.IOException

class Profile : BaseActivityUser() {
    lateinit var binding: ProfileBinding
    private lateinit var viewModal: ProfileViewModal
    private var bitmap: Bitmap? = null
    private var imageFile: File? = null
    private lateinit var imageUri: Uri
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.profile)
        viewModal = ViewModelProvider(this@Profile).get(ProfileViewModal::class.java)

        init()
        setObserver()

        profileView()
    }

    private fun init() {
        toolbar_back.setOnClickListener {
            finish()
        }
        toolbar_title.text = getString(R.string.editProfile)
        binding.userProfileCircleImg.setOnClickListener {
            selectImage()
        }
        binding.tvSave.setOnClickListener {
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        checkCameraPermission()
    }
    private fun checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE),
                Constants.REQUEST_PERMISSION
            )
        }
    }
    private fun selectImage() {
        try {
            val pm = packageManager
            val hasPerm =
                pm.checkPermission(Manifest.permission.CAMERA, packageName)
            val hasPerm1 = pm.checkPermission(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                packageName
            )
            if (hasPerm == PackageManager.PERMISSION_GRANTED && hasPerm1 == PackageManager.PERMISSION_GRANTED) {
                val options =
                    arrayOf<CharSequence>("Take Photo", "Choose From Gallery", "Cancel")
                val builder =
                    AlertDialog.Builder(this)
                builder.setTitle("Select Option")
                builder.setItems(
                    options
                ) { dialog, item ->
                    when {
                        options[item] == "Take Photo" -> {
                            dialog.dismiss()
                            openCamera()
                        }
                        options[item] == "Choose From Gallery" -> {
                            dialog.dismiss()
                            openGallery()
                        }
                        options[item] == "Cancel" -> {
                            dialog.dismiss()
                        }
                    }
                }
                builder.show()
            } else
                checkCameraPermission()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun openCamera() {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, "New Picture")
        values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera")
        imageUri =
            contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)!!

        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
        startActivityForResult(intent, Constants.CAMERA_REQUEST)
    }

    private fun openGallery() {
        val galleryIntent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        startActivityForResult(galleryIntent, Constants.GALLERY_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            // There are no request codes
            val data = data?.data
            // doSomeOperations()
            if (requestCode == Constants.CAMERA_REQUEST) {
                if (Build.VERSION.SDK_INT >= 29) {
                    val source: ImageDecoder.Source =
                        ImageDecoder.createSource(applicationContext.contentResolver, imageUri)
                    try {
                        bitmap = ImageDecoder.decodeBitmap(source)
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                } else {
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(
                            applicationContext.contentResolver,
                            imageUri
                        )
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
                binding.userProfileCircleImg.setImageBitmap(bitmap)
                //imageFile = File(Utils.getRealPathFromURI(this, imageUri)!!)
            } else if (requestCode == Constants.GALLERY_REQUEST) {
                val uri = data
                if (Build.VERSION.SDK_INT >= 29) {
                    val source: ImageDecoder.Source =
                        ImageDecoder.createSource(applicationContext.contentResolver, uri!!)
                    try {
                        bitmap = ImageDecoder.decodeBitmap(source)
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                } else {
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(
                            applicationContext.contentResolver,
                            uri
                        )
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
                binding.userProfileCircleImg.setImageBitmap(bitmap)
                //imageFile = File(Utils.getRealPathFromURI(this, uri)!!)
            }
        }
    }
    private fun profileView() {
        viewModal.profileView()
    }


    private fun setObserver() {
        viewModal.profileViewResponse.observe(this) { response ->
            Log.d("ressponsexcxmessage ",response.data.toString())

            when (response) {
                is Resource.Success -> {
                    ProgressDialog.hideProgressBar()
                   edtName.setText(response.data?.username)
                   edtEmail.setText(response.data?.email)
                   edtPhoneNumber.setText(response.data?.mobile)
                    tvSelectGender.text=response.data?.gender
                    tvDob.text= response.data?.dob.toString()
                }
                is Resource.Loading -> {
                    ProgressDialog.showProgressBar(this)
                }
                is Resource.Error -> {
                    ProgressDialog.hideProgressBar()
                    Toast.makeText(
                        applicationContext,
                        response.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}