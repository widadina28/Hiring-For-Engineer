package com.ros.hiringapkforengineer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.ros.hiringapkforengineer.databinding.ActivityOnboardBinding
import com.ros.hiringapkforengineer.login.LoginActivity
import com.ros.hiringapkforengineer.register.RegisterActivity

class Onboard : AppCompatActivity() {
    private lateinit var binding: ActivityOnboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
       binding = DataBindingUtil.setContentView(this, R.layout.activity_onboard)
        binding.ivRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
        binding.ivLogin.setOnClickListener {
            Toast.makeText(this,"Login", Toast.LENGTH_LONG).show()
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}