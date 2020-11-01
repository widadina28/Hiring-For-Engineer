package com.ros.hiringapkforengineer.profile.portofolio.post

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ros.hiringapkforengineer.API.ApiClient
import com.ros.hiringapkforengineer.R
import com.ros.hiringapkforengineer.bottomnav.BottomNavActivity
import com.ros.hiringapkforengineer.databinding.FragmentAddPortfolioBinding
import com.ros.hiringapkforengineer.home.experience.ExperienceApiService
import com.ros.hiringapkforengineer.home.portofolio.PortofolioApiService
import com.ros.hiringapkforengineer.profile.edit.EditProfileActivity
import com.ros.hiringapkforengineer.profile.engineer.ProfileFragment
import com.ros.hiringapkforengineer.utils.Constant
import com.ros.hiringapkforengineer.utils.SharedPrefUtil
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class AddPortfolioFragment : AppCompatActivity() {
    private lateinit var binding: FragmentAddPortfolioBinding
    private lateinit var sharedpref: SharedPrefUtil
    private lateinit var viewModel: AddPortfolioViewModel
    private var selectedType = ""

    private var img: MultipartBody.Part? = null

    companion object {
        private val IMAGE_PICK_CODE = 1000
        val PERMISSION_CODE = 1001
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.fragment_add_portfolio)
        sharedpref = SharedPrefUtil(this)
        val service = ApiClient.getApiClient(this)?.create(PortofolioApiService::class.java)
        viewModel = ViewModelProvider(this).get(AddPortfolioViewModel::class.java)
        viewModel.setSharedPreferences(sharedpref)
        if (service != null) {
            viewModel.setServicePortfolio(service)
        }

        subscribeLiveData()
        setUpListener()
        setCheckedListener()

    }

    fun subscribeLiveData() {
        viewModel.isPostResponse.observe(this, Observer {
            if (it) {
                Toast.makeText(this, "Add Portfolio Success!", Toast.LENGTH_LONG).show()
                startActivity(Intent(this, BottomNavActivity::class.java))
            } else {
                Toast.makeText(this, "Add Portfolio Failed", Toast.LENGTH_LONG).show()
            }

        })

    }

    fun setCheckedListener() {
        val option = binding.radioGroup
        option.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { radioGroup, id ->
            when (id) {
                R.id.mobile -> selectedType = "Mobile"
                R.id.web -> selectedType = "Web"
            }
        })
    }

    fun setUpListener() {
        binding.btnAddPort.setOnClickListener {
            val id = sharedpref.getString(Constant.PREF_ID_ENGINEER_PROFILE).toString()
            viewModel.postPortfolio(
                createPartFromString(id),
                createPartFromString(binding.etNamePortfolio.text.toString()),
                createPartFromString(binding.etLinkRepo.text.toString()),
                img,
                createPartFromString(selectedType)
            )
        }
        binding.imgUpload.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                    val permissions = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    requestPermissions(permissions, PERMISSION_CODE)
                } else {
                    pickImgGallery()
                }
            } else {
                pickImgGallery()
            }

        }
    }

    private fun pickImgGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            EditProfileActivity.PERMISSION_CODE -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickImgGallery()
                } else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            binding.imgUpload.setImageURI(data?.data)
            val filePath = getPath(this, data?.data)
            val file = File(filePath)

            val mediaTypeImg = "image/jpeg".toMediaType()
            val inputStream = contentResolver.openInputStream(data?.data!!)
            val reqFile: RequestBody? = inputStream?.readBytes()?.toRequestBody(mediaTypeImg)
            img = reqFile?.let { it1 ->
                MultipartBody.Part.createFormData(
                    "image", file.name,
                    it1
                )
            }


        }
    }

    fun getPath(context: Context, uri: Uri?): String {
        var result: String? = null
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val cursor: Cursor? =
            uri?.let { context.contentResolver.query(it, proj, null, null, null) }
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                val column_index = cursor.getColumnIndexOrThrow(proj[0])
                result = cursor.getString(column_index)
            }
            cursor.close()
        }
        if (result == null) {
            result = "Not found"
        }
        return result
    }

    @NonNull
    private fun createPartFromString(json: String): RequestBody {
        val mediaType = "multipart/form-data".toMediaType()
        return json
            .toRequestBody(mediaType)
    }
}