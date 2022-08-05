package com.farmershop.ui.activity

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.farmershop.R
import com.farmershop.data.model.PopupDropDownListModel
import com.farmershop.data.model.request.UpdateProfileRequest
import com.farmershop.data.viewModel.ProfileViewModal
import com.farmershop.databinding.ProfileBinding
import com.farmershop.ui.base.BaseActivityUser
import com.farmershop.utils.*
import com.farmershop.utils.Utility.nullCheck
import kotlinx.android.synthetic.main.include_toolbar.*
import kotlinx.android.synthetic.main.profile.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.IOException


class Profile : BaseActivityUser() {
    lateinit var binding: ProfileBinding
    private lateinit var viewModal: ProfileViewModal
    private var bitmap: Bitmap? = null
    private var imageFile: File? = null
    private lateinit var imageUri: Uri
    private var stateId = ""
    private var arrayListSelectGender: ArrayList<PopupDropDownListModel> = ArrayList()
    private var arrayListState: ArrayList<PopupDropDownListModel> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.profile)
        viewModal = ViewModelProvider(this@Profile).get(ProfileViewModal::class.java)

        init()
        setObserver()
        profileView()
        getState()
    }

    private fun init() {
        toolbar_back.setOnClickListener {
            onBackPressed()
        }
        toolbar_title.text = getString(R.string.editProfile)
        binding.edtUserName.isEnabled = false
        binding.edtUserName.isFocusable = false
        binding.edtUserName.setTextColor(ContextCompat.getColor(this, R.color.colorGrayLight))
        binding.edtEmail.isEnabled = false
        binding.edtEmail.isFocusable = false
        binding.edtEmail.setTextColor(ContextCompat.getColor(this, R.color.colorGrayLight))
        binding.edtPhoneNumber.isEnabled = false
        binding.edtPhoneNumber.isFocusable = false
        binding.edtPhoneNumber.setTextColor(ContextCompat.getColor(this, R.color.colorGrayLight))

        binding.userProfileCircleImg.setOnClickListener {
            selectImage()
        }
        arrayListSelectGender.add(PopupDropDownListModel("1", "Male"))
        arrayListSelectGender.add(PopupDropDownListModel("2", "Female"))
        arrayListSelectGender.add(PopupDropDownListModel("3", "Other"))
        binding.tvSelectGender.setOnClickListener {
            Utility.showPopUpWindow(this, arrayListSelectGender, object : DropDownListInterface {
                override fun onSelectItem(
                    position: Int,
                    arrayList: ArrayList<PopupDropDownListModel>
                ) {
                    binding.tvSelectGender.text = arrayList[position].name
                }
            }, false)
        }
        binding.tvSelectState.setOnClickListener {
            Utility.showPopUpWindow(this, arrayListState, object : DropDownListInterface {
                override fun onSelectItem(
                    position: Int,
                    arrayList: ArrayList<PopupDropDownListModel>
                ) {
                    binding.tvSelectState.text = arrayList[position].name
                    stateId = arrayList[position].id
                }
            }, true)
        }
        binding.tvDob.setOnClickListener {
            Utility.datePicker(this, object : DatePickerInterface {
                override fun onClick(value: String) {
                    binding.tvDob.text = value
                }
            })
        }
        binding.tvSave.setOnClickListener {
            validation()
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
                imageFile = File(Utility.getRealPathFromURI(this, imageUri)!!)
                updateProfilePhoto(imageFile!!)
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
                imageFile = File(Utility.getRealPathFromURI(this, uri)!!)
                updateProfilePhoto(imageFile!!)
            }
        }
    }

    private fun profileView() {
        viewModal.profileView()
    }

    private fun getState() {
        viewModal.getState()
    }

    private fun updateProfilePhoto(file: File) {
        val requestFile: RequestBody =
            RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file)
        val body: MultipartBody.Part =
            MultipartBody.Part.createFormData("photo", file.name, requestFile)
        val username: RequestBody = RequestBody.create(
            "multipart/form-data".toMediaTypeOrNull(),
            AppSession.getUserName().toString()
        )
        viewModal.updateProfilePhoto(username, body)
    }

    private fun updateProfile() {
        val name = binding.edtName.text.toString()
        val address = binding.edtAddress.text.toString()
        val city = binding.edtCity.text.toString()
        val zip = binding.edtZip.text.toString()
        val dob = binding.tvDob.text.toString()
        val state = stateId
        val gender = binding.tvSelectGender.text.toString()
        val request = UpdateProfileRequest(address, city, dob, gender, name, state, zip)
        viewModal.updateProfile(AppSession.getUserName()!!, request)
    }

    private fun validation() {
        when {
            Validation.isEmpty(binding.edtName.text.toString()) -> {
                StaticMethods.alert(this, getString(R.string.name_required))
            }
            binding.tvSelectGender.text.toString() == getString(R.string.select_gender) -> {
                StaticMethods.alert(this, getString(R.string.gender_required))
            }
            binding.tvDob.text.toString() == getString(R.string.select_dob) -> {
                StaticMethods.alert(this, getString(R.string.dob_required))
            }
            Validation.isEmpty(binding.edtAddress.text.toString()) -> {
                StaticMethods.alert(this, getString(R.string.address_required))
            }
            binding.tvSelectState.text.toString() == getString(R.string.select_state) -> {
                StaticMethods.alert(this, getString(R.string.state_required))
            }
            Validation.isEmpty(binding.edtCity.text.toString()) -> {
                StaticMethods.alert(this, getString(R.string.city_required))
            }
            Validation.isEmpty(binding.edtZip.text.toString()) -> {
                StaticMethods.alert(this, getString(R.string.pincode_required))
            }
            else -> {
                Utility.hideKeyboard(this)
                updateProfile()
            }
        }
    }

    private fun setObserver() {
        viewModal.profileViewResponse.observe(this) { response ->
            Log.d("ressponsexcxmessage ", response.data.toString())

            when (response) {
                is Resource.Success -> {
                    ProgressDialog.hideProgressBar()
                    binding.edtName.setText(nullCheck(response.data?.name.toString()))
                    binding.edtUserName.setText(nullCheck(response.data?.username.toString()))
                    binding.edtEmail.setText(nullCheck(response.data?.email.toString()))
                    binding.edtPhoneNumber.setText(nullCheck(response.data?.mobile.toString()))
                    if(nullCheck(response.data?.gender.toString())==""){
                        binding.tvSelectGender.text = getString(R.string.select_gender)
                    }else{
                        binding.tvSelectGender.text = nullCheck(response.data?.gender.toString())
                    }
                    if(nullCheck(response.data?.dob.toString())==""){
                        binding.tvDob.text = getString(R.string.select_dob)
                    }else{
                        binding.tvDob.text = nullCheck(response.data?.dob.toString())
                    }
                    if(nullCheck(response.data?.state_name.toString()) == ""){
                        binding.tvSelectState.text = getString(R.string.select_state)
                    }else{
                        binding.tvSelectState.text = nullCheck(response.data?.state_name.toString())
                    }
                    binding.edtCity.setText(nullCheck(response.data?.city.toString()))
                    binding.edtZip.setText(nullCheck(response.data?.zip.toString()))
                    binding.edtAddress.setText(nullCheck(response.data?.address.toString()))
                    Glide.with(this)
                        .applyDefaultRequestOptions(
                            RequestOptions()
                                .placeholder(R.drawable.user)
                                .error(R.drawable.user)
                        )
                        .load(response.data?.photo.toString())
                        .into(binding.userProfileCircleImg)
                    stateId = response.data?.state_id.toString()
                    /*      Glide.with(this)
                              .load(response.data?.photo.toString())
                              .centerCrop()
                              .placeholder(R.drawable.user)
                              .into(binding.userProfileCircleImg)*/

                    updateProfileDataInLocal(
                        nullCheck(response.data?.name.toString()),
                        nullCheck(response.data?.email.toString()),
                        nullCheck(response.data?.mobile.toString()),
                        nullCheck(response.data?.username.toString()),
                        nullCheck(response.data?.gender.toString()),
                        nullCheck(response.data?.id.toString()),
                        nullCheck(response.data?.photo.toString()),
                        nullCheck(response.data?.address.toString()),
                        nullCheck(response.data?.dob.toString()),
                        nullCheck(response.data?.zip.toString()),
                        nullCheck(response.data?.state_id.toString()),
                        nullCheck(response.data?.state_name.toString()),
                        nullCheck(response.data?.city.toString()),
                        nullCheck(response.data?.aadhar_no.toString())
                    )
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
        viewModal.stateResponse.observe(this) { response ->
            Log.d("ressponsexcxmessage ", response.data.toString())

            when (response) {
                is Resource.Success -> {
                    ProgressDialog.hideProgressBar()
                    arrayListState.clear()
                    for (i in response.data!!) {
                        arrayListState.add(PopupDropDownListModel(i.id.toString(), i.state!!))
                    }
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

        viewModal.updateProfileResponse.observe(this) { response ->
            Log.d("ressponsexcxmessage ", response.data.toString())

            when (response) {
                is Resource.Success -> {
                    ProgressDialog.hideProgressBar()
                    profileView()
                    Toast.makeText(this, response.data, Toast.LENGTH_LONG).show()
                }
                is Resource.Loading -> {
                    ProgressDialog.showProgressBar(this)
                }
                is Resource.Error -> {
                    ProgressDialog.hideProgressBar()
                    Toast.makeText(
                        applicationContext,
                        response.data,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
        viewModal.updateProfilePhotoResponse.observe(this) { response ->
            Log.d("ressponsexcxmessage ", response.data.toString())

            when (response) {
                is Resource.Success -> {
                    ProgressDialog.hideProgressBar()
                    Toast.makeText(this, response.data, Toast.LENGTH_LONG).show()
                }
                is Resource.Loading -> {
                    ProgressDialog.showProgressBar(this)
                }
                is Resource.Error -> {
                    ProgressDialog.hideProgressBar()
                    Toast.makeText(
                        applicationContext,
                        response.data,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun updateProfileDataInLocal(
        name: String, email: String, mobile: String, username: String,
        gender: String, userId: String, imageUrl: String, address: String,
        dob: String, zip: String, stateId: String, state: String, city: String,
        aadhaarNo: String
    ) {
        AppSession.setUserId(userId)
        AppSession.setName(name)
        AppSession.setEmail(email)
        AppSession.setMobile(mobile)
        AppSession.setPhoto(imageUrl)
        AppSession.setAadhaarNo(aadhaarNo)
        AppSession.setUserName(username)
        AppSession.setGender(gender)
        AppSession.setAddress(address)
        AppSession.setDob(dob)
        AppSession.setZip(zip)
        AppSession.setStateID(stateId)
        AppSession.setState(state)
        AppSession.setCity(city)
    }
}