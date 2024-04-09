package com.intelliflow.apps.repository

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.intelliflow.apps.model.LoginResponse
import com.intelliflow.apps.model.ServicesSetterGetter
import com.intelliflow.apps.model.SignInBody
import com.intelliflow.apps.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object MainActivityRepository {

    val responseVal = MutableLiveData<LoginResponse>()
    //  val signInInfo = SignInBody("0Zgz+xCyFMWrIMd4Wdyu3w==", "info@intelliflow.io")

    private var Tag: String = " MainActivityRepository "

    fun getServicesApiCall(signInInfo: SignInBody): MutableLiveData<LoginResponse> {

        Log.d(Tag, " signinbody $signInInfo")


        val call = RetrofitClient.apiInterface.signin(signInInfo)
        call.enqueue(object : Callback<LoginResponse> {

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                // TODO("Not yet implemented")
                Log.d("$Tag onFailure : ", t.message.toString())


            }

            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {

                var message = response.message()

                Log.d(Tag, "message is $message")

                // TODO("Not yet implemented")
                Log.d("onResponse : ", response.body().toString())
                if (response.body() != null) {

                    val data = response.body()
                    val accessInfo = data!!.accessInfo
                    var userInfo=data!!.UserInfo
                    responseVal.value = LoginResponse(accessInfo,userInfo)

                }

                else{

                    responseVal.value = LoginResponse(null,null)

                }

//
//                var body=   call.request().body()
//                var url=    call.request().url().url().toString()
//
//             var contentType=   call.request().body()?.contentType()
//
//                Log.d(Tag, " body is $body")
//
//                Log.d(Tag, " contentType is $contentType")
//
//                Log.d(Tag, " url is $url")


            }
        })

        return responseVal
    }

}