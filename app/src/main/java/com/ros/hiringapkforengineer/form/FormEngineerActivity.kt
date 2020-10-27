package com.ros.hiringapkforengineer.form

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.databinding.adapters.AdapterViewBindingAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ros.hiringapkforengineer.API.ApiClient
import com.ros.hiringapkforengineer.R
import com.ros.hiringapkforengineer.bottomnav.BottomNavActivity
import com.ros.hiringapkforengineer.databinding.ActivityFormEngineerBinding
import com.ros.hiringapkforengineer.form.skill.FormSkillActivity
import com.ros.hiringapkforengineer.utils.Constant
import com.ros.hiringapkforengineer.utils.SharedPrefUtil
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class FormEngineerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFormEngineerBinding
    private lateinit var sharedpref: SharedPrefUtil
    private lateinit var viewModel: FormProfileViewModel
    private var selectedStatus = ""
    private var selectedJob = ""
    private var selectedLoc = ""

    private var img: MultipartBody.Part? = null
    companion object {
        private val IMAGE_PICK_CODE = 1000
        val PERMISSION_CODE = 1001
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       binding = DataBindingUtil.setContentView(this, R.layout.activity_form_engineer)
        sharedpref = SharedPrefUtil(applicationContext)
        val service = ApiClient.getApiClient(this)?.create(PostProfileApiService::class.java)
        viewModel = ViewModelProvider(this).get(FormProfileViewModel::class.java)
        viewModel.setSharedPref(sharedpref)

        if (service!=null) {
            viewModel.setFormService(service)
        }

        spinnerStatus()

        setUpListener()
        viewModel.spinnerLoc()
        viewModel.spinnerJob()


        subscribeLiveData()
    }

    private fun spinnerStatus(){
        val spinnerStatus = binding.spinnerStatus as Spinner
        var status = arrayOf("Freelance", "Fulltime")
        spinnerStatus.adapter = ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, status)
        spinnerStatus.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                selectedStatus = status[position].toString()
            }

        }

    }


    private fun setUpListener(){
        binding.btnNext.setOnClickListener {
            val id = sharedpref.getString(Constant.PREF_ID_ACC)
            viewModel.requestEngineer(
                createPartFromString(binding.etName.text.toString()),
                createPartFromString(selectedJob),
                createPartFromString(selectedLoc),
                createPartFromString("5000000"),
                createPartFromString("4.5"),
                createPartFromString(binding.etDescription.text.toString()),
                img,
                createPartFromString(selectedStatus),
                createPartFromString("$id")
            )
        }
        binding.btnEditPhoto.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                    val permissions = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    requestPermissions(permissions, PERMISSION_CODE)
                }
                else {
                    pickImgGallery()
                }
            } else {
                pickImgGallery()
            }
        }
    }

    private fun subscribeLiveData(){
        viewModel.isJobSpinner.observe(this, Observer {
            val spinnerJob = binding.spinnerJob as Spinner
            spinnerJob.adapter = ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, it.map {
                it.nameFreelance
            })
            spinnerJob.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onNothingSelected(p0: AdapterView<*>?) {

                }

                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                    selectedJob = it[position].idFreelance.toString()
                }

            }
        })
        viewModel.isLocationSpinner.observe(this, Observer {
            val spinnerLoc = binding.spinnerLocation as Spinner
            spinnerLoc.adapter = ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, it.map {
                it.nameLoc
            })
            spinnerLoc.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onNothingSelected(p0: AdapterView<*>?) {

                }

                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                    selectedLoc = it[position].idLoc.toString()
                }

            }
        })
        viewModel.isRequestResponse.observe(this, Observer {
            if (it) {

                startActivity(Intent(this, FormSkillActivity::class.java))
            }
            else {
                Toast.makeText(this, "Failed!", Toast.LENGTH_LONG).show()
            }

        })

    }

    private fun pickImgGallery(){
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
            PERMISSION_CODE -> {
                if (grantResults.size > 0 && grantResults [0] == PackageManager.PERMISSION_GRANTED) {
                    pickImgGallery()
                }
                else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            binding.circleImageView3.setImageURI(data?.data)
            val filePath = getPath(this, data?.data)
            val file = File(filePath)

            val mediaTypeImg = "image/jpeg".toMediaType()
            val inputStream = contentResolver.openInputStream(data?.data!!)
            val reqFile: RequestBody? = inputStream?.readBytes()?.toRequestBody(mediaTypeImg)
            img = reqFile?.let { it1 ->
                MultipartBody.Part.createFormData("image", file.name,
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