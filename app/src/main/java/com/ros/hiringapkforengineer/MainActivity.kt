package com.ros.hiringapkforengineer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.databinding.DataBindingUtil
import com.ros.hiringapkforengineer.bottomnav.BottomNavActivity
import com.ros.hiringapkforengineer.databinding.ActivityMainBinding
import com.ros.hiringapkforengineer.utils.Constant
import com.ros.hiringapkforengineer.utils.SharedPrefUtil

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val timesplash : Long = 3000
    lateinit var sharedpref : SharedPrefUtil


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        sharedpref = SharedPrefUtil(applicationContext)
        val token = sharedpref.getString(Constant.PREF_TOKEN)


        Handler().postDelayed({
            if (token != null) {
                startActivity(Intent(this, BottomNavActivity::class.java))
                finish()
            }
            else {
                startActivity(Intent(this, Onboard::class.java))
            }
        }, timesplash)
    }
}