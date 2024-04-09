package com.intelliflow.apps.repository

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.intelliflow.apps.model.LoginResponse
import com.intelliflow.apps.model.AppsRecentActionsModel
import com.intelliflow.apps.retrofit.RetrofitClientAppsRecentActions
import com.intelliflow.apps.retrofit.RetrofitClientRecentActions
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object AppsRecentActivityRepository {

    val responseVal = MutableLiveData<AppsRecentActionsModel>()

    private var Tag: String = " RecentActionsRepository "

    fun getAppsRecentActions(baseUrl:String): MutableLiveData<AppsRecentActionsModel> {
        RetrofitClientAppsRecentActions.BASE_URL =baseUrl
      //  Log.d(Tag, " baseurl $baseUrl")
        Log.d(Tag, " RetrofitClientRecentActions ${RetrofitClientRecentActions.BASE_URL}")
        val call = RetrofitClientAppsRecentActions.apiInterface.getAppsRecentActions1(baseUrl,"info@intelliflow.io")
        call.enqueue(object : Callback<AppsRecentActionsModel> {
            override fun onFailure(call: Call<AppsRecentActionsModel>, t: Throwable) {
                // TODO("Not yet implemented")
                Log.d("$Tag onFailure ", t.message.toString())
                responseVal.value = AppsRecentActionsModel(null)
            }
            override fun onResponse(
                call: Call<AppsRecentActionsModel>,
                response: Response<AppsRecentActionsModel>
            ) {
                var message = response.message()
                Log.d(Tag, "message is $message")
                // TODO("Not yet implemented")
                Log.d("onResponse : ", response.body().toString())
                if (response.body() != null) {
                    val data = response.body()!!.data
                    responseVal.value = AppsRecentActionsModel(null,data)
                }
                else{
                    responseVal.value = AppsRecentActionsModel(null)
                }
            }
        })

        return responseVal
    }

}