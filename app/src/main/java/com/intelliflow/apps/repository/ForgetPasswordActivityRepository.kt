package com.intelliflow.apps.repository

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.intelliflow.apps.model.AccessInfo
import com.intelliflow.apps.model.LoginResponse
import com.intelliflow.apps.model.ServicesSetterGetter
import com.intelliflow.apps.model.SignInBody
import com.intelliflow.apps.model.forgetpassword.ForgetPasswordBody
import com.intelliflow.apps.model.forgetpassword.ForgetPasswordModel
import com.intelliflow.apps.retrofit.RetrofitClient
import com.intelliflow.apps.retrofit.RetrofitClientPassword
import com.intelliflow.apps.view.password.ForgotPassword
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object ForgetPasswordActivityRepository {

    private val forgetPasswordResponse = MutableLiveData<ForgetPasswordModel>()
 //   val signInInfo = SignInBody("password", "info@intelliflow.io")
 private val Tag: String="DashBoardActivityRepo"
    fun getForgetPasswordModelCall(forgetPasswordBody: ForgetPasswordBody): MutableLiveData<ForgetPasswordModel> {

        val call = RetrofitClientPassword.apiInterface.forgetPassword(forgetPasswordBody)

        call.enqueue(object: Callback<ForgetPasswordModel> {

            override fun onFailure(call: Call<ForgetPasswordModel>, t: Throwable) {

                Log.d("$Tag DEBUG : ", t.message.toString())
            }

            override fun onResponse(
                call: Call<ForgetPasswordModel>,
                response: Response<ForgetPasswordModel>
            ) {

                // TODO("Not yet implemented")
                Log.d("DEBUG : ", response.body().toString())

                val data = response.body()

                if (response.body() != null) {

                    val data = response.body()
                    val msg = data!!.message
                    val status =data!!.status
                    forgetPasswordResponse.value = ForgetPasswordModel(status,msg)

                }

                else{
                    forgetPasswordResponse.value = ForgetPasswordModel(null,null)
                }
            }
        }

        )

        return forgetPasswordResponse
    }

}