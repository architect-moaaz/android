package com.intelliflow.apps.repository


import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.intelliflow.apps.data.model.Apps
import com.intelliflow.apps.data.model.MyApps
import com.intelliflow.apps.model.SignInBody
import com.intelliflow.apps.retrofit.RetrofitClient
import com.intelliflow.apps.retrofit.RetrofitClientDash
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object DashBoardActivityRepository {

    private val Tag: String="DashBoardActivityRepo"
    var apps = MutableLiveData<MyApps>()
    val errorMessage = MutableLiveData<String>()
    val signInInfo = SignInBody("password", "info@intelliflow.io")

    fun getServicesApiCall(): MutableLiveData<MyApps> {

        val call = RetrofitClientDash.apiInterface.getApps()
        Log.d(Tag, "getServicesApiCall ")
        call.enqueue(object:Callback<MyApps>{
            override fun onResponse(call: Call<MyApps>, response: Response<MyApps>) {

                Log.d(Tag, " response $response")
                apps.postValue(response.body())
           //   apps=
             //   Log.d(Tag, "It is working fine $response")


            }

            override fun onFailure(call: Call<MyApps>, t: Throwable) {
                Log.d(Tag," $Tag onFailure "+ t.message.toString())
                errorMessage.postValue(t.message)
            }

        })



        return apps
    }

}


