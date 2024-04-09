package com.intelliflow.apps.view.password

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputLayout
import com.intelliflow.apps.R
import com.intelliflow.apps.model.SignInBody
import com.intelliflow.apps.model.forgetpassword.ForgetPasswordBody
import com.intelliflow.apps.view.dashboard.Dashboard
import com.intelliflow.apps.view.login.LoginActivity
import com.intelliflow.apps.viewmodel.ForgetPasswordViewModel
import com.intelliflow.apps.viewmodel.LoginActivityViewModel

class ForgotPassword : AppCompatActivity() {


    private val Tag = "ForgotPassword"
    lateinit var email:String
    lateinit var forgetPasswordViewModel: ForgetPasswordViewModel
    var forgetPasswordEmail : TextInputLayout? =null
  //  var hvaText="<div style=\"color:black;\">Have an Account? <div style=\"color:blue;\" >Log In</div></div>" + ""
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        var hva : TextView = findViewById<TextView>(R.id.hvaText1)
        forgetPasswordEmail  = findViewById<TextInputLayout>(R.id.forgetPassword_email)
        var resetPasswordBtn : Button = findViewById<Button>(R.id.btnAuth)
//        hva.text =
//            Html.fromHtml(hvaText, Html.FROM_HTML_MODE_COMPACT)
        hva.setOnClickListener {
            Intent(this, ResetPassword::class.java).also {
                startActivity(it)
            }
        }

     //   forgetPassword.text

        resetPasswordBtn.setOnClickListener {

            if(validateEmail()) {
                forgetPassword()
            }

        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window?.statusBarColor = getColor(R.color.colorPrimaryDark)
        }

    }

   fun validateEmail(): Boolean{
       email = forgetPasswordEmail?.editText?.text.toString()
        if (email!!.isEmpty()) {
            forgetPasswordEmail?.error = "Email Required"
            forgetPasswordEmail?.requestFocus()
            return false
        }

        if (email!!.isEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            forgetPasswordEmail?.error = "Enter Valid EmailID"
            forgetPasswordEmail?.requestFocus()
            return false
        }

       return true

    }



    private fun forgetPassword(){


        forgetPasswordViewModel = ViewModelProvider(this)[ForgetPasswordViewModel::class.java]
        Log.d(Tag, " email is $email")
        val fbody= ForgetPasswordBody(email)
        forgetPasswordViewModel.forgetPasswordModel(fbody)!!.

        observe(this, Observer { response ->
            Log.d(Tag, "status is $response")

            var status = response.status

          val message=  response.message

            Log.d(Tag, "status is $status")

            if(status.equals("Success")){

                startActivity(Intent(this@ForgotPassword, LoginActivity::class.java))
                finish()
            }

            else   if(status.equals("Failure")){
                forgetPasswordEmail?.editText?.setText("")
                Toast.makeText(this,message, Toast.LENGTH_LONG).show()
            }

            status =null

        }

        )


    }




}