package com.gproduction.yuklapor.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.google.firebase.auth.FirebaseAuth
import com.gproduction.yuklapor.R
import com.gproduction.yuklapor.tools.SharedPreferences
import com.gproduction.yuklapor.ui.auth.AuthActivity
import com.gproduction.yuklapor.ui.home.HomeActivityAdmin
import com.gproduction.yuklapor.ui.home.HomeActivityMasyarakat

class SplashScreenActivity : AppCompatActivity() {

    private val sharedPreferences by lazy{
        SharedPreferences(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler().postDelayed({
            val firebaseAuth = FirebaseAuth.getInstance()
            if (firebaseAuth.currentUser != null){
                when(sharedPreferences.getRole()){
                    0 -> {
                        val intent = Intent(this, HomeActivityMasyarakat::class.java)
                        startActivity(intent)
                    }
                    1 -> {
                        val intent = Intent(this,
                            HomeActivityAdmin::class.java)
                        startActivity(intent)
                    }
                }
            }
            else{
                val intent = Intent(this,AuthActivity::class.java)
                startActivity(intent)
            }
            finish()
        },2000)
    }
}
