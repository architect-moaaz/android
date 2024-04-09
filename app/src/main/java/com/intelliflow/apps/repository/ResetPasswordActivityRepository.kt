package com.intelliflow.apps.repository

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.intelliflow.apps.model.AccessInfo
import com.intelliflow.apps.model.ServicesSetterGetter
import com.intelliflow.apps.model.SignInBody
import com.intelliflow.apps.model.forgetpassword.ForgetPasswordModel
import com.intelliflow.apps.model.forgetpassword.ResetPasswordModel
import com.intelliflow.apps.retrofit.RetrofitClient
import com.intelliflow.apps.retrofit.RetrofitClientPassword
import com.intelliflow.apps.view.password.ForgotPassword
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object ResetPasswordActivityRepository {

    private val serviceSetterGetter = MutableLiveData<ResetPasswordModel>()
 //   val signInInfo = SignInBody("password", "info@intelliflow.io")
 private val Tag: String="ResetPasswordActivityRepository"
    fun resetPasswordModelCall(): MutableLiveData<ResetPasswordModel> {

        val call = RetrofitClientPassword.apiInterface.resetPassword()

        call.enqueue(object: Callback<ResetPasswordModel> {

            override fun onFailure(call: Call<ResetPasswordModel>, t: Throwable) {
                // TODO("Not yet implemented")
                Log.v("$Tag onFailure : ", t.message.toString())
            }

            override fun onResponse(
                call: Call<ResetPasswordModel>,
                response: Response<ResetPasswordModel>
            ) {

                // TODO("Not yet implemented")
                Log.v("DEBUG : ", response.body().toString())

                val data = response.body()

             //   val msg = data!!.accessToken

             //   serviceSetterGetter.value = ForgotPassword(msg)
            }
        }

        )

        return serviceSetterGetter
    }

}