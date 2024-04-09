package com.intelliflow.apps.view.password

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.intelliflow.apps.R
import com.intelliflow.apps.view.login.LoginActivity
import com.intelliflow.apps.viewmodel.ForgetPasswordViewModel

class ResetPassword : AppCompatActivity() {

    private val Tag= "ForgotPassword"
    lateinit var forgetPasswordViewModel: ForgetPasswordViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window?.statusBarColor = getColor(R.color.colorPrimaryDark)
        }

        var resetPasswordBtn:Button = findViewById<Button>(R.id.resetPasswordBtn)

        resetPasswordBtn.setOnClickListener(View.OnClickListener {

            resetPassword()

        })

//        var hva:TextView = findViewById<TextView>(R.id.hva)
//        hva.setOnClickListener {
//            Intent(this, LoginActivity::class.java).also {
//                startActivity(it)
//            }
//        }
    }

    private fun resetPassword(){


        forgetPasswordViewModel = ViewModelProvider(this)[ForgetPasswordViewModel::class.java]

//        forgetPasswordViewModel.forgetPasswordModel()!!.
//
//        observe(this, Observer { response ->
//
//
//            val msg = response.status
//
//            Log.d(Tag, "msg is $msg")
//
//            if(msg!=null){
//
//                startActivity(Intent(this@ResetPassword, LoginActivity::class.java))
//
//            }
//
//            else{
//
//                Toast.makeText(this, "Please Enter Valid EmailID ", Toast.LENGTH_LONG).show()
//
//            }
//
//        }
//
//        )

    }



}