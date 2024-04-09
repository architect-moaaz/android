package com.intelliflow.apps.splash

import android.content.Context
import android.content.Intent
import android.os.*
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import com.intelliflow.apps.R
import com.intelliflow.apps.databinding.ActivityMainBinding
import com.intelliflow.apps.view.dashboard.Dashboard
import com.intelliflow.apps.view.login.LoginActivity
import com.intelliflow.apps.view.recentactions.SignatureActivity
import com.intelliflow.apps.view.recentactions.SignatureActivity1


class SplashScreen : AppCompatActivity() {


    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView(R.layout.splashscreen)
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({

            var sharedPreference = getSharedPreferences("emailSave", Context.MODE_PRIVATE)
           // editor?.putBoolean("isLoggedin", true)
            var savedEmail = sharedPreference?.getBoolean("isLoggedin",false)
            Log.d("SplashScreen", "savedEmail $savedEmail")
            if (savedEmail == true) {
                startActivity(Intent(this@SplashScreen, Dashboard::class.java))
                finish()
            } else {
                startActivity(Intent(this@SplashScreen, LoginActivity::class.java))
              //  startActivity(Intent(this@SplashScreen, SignatureActivity::class.java))
                finish()
            }


        }, 3000)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window?.statusBarColor = getColor(R.color.colorPrimaryDark)
        }

    }

}