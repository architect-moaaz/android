package com.intelliflow.apps.repository

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.intelliflow.apps.model.LoginResponse
import com.intelliflow.apps.model.ServicesSetterGetter
import com.intelliflow.apps.model.SignInBody
import com.intelliflow.apps.model.recentactions.Recentsidemenumodel
import com.intelliflow.apps.retrofit.RetrofitClient
import com.intelliflow.apps.retrofit.RetrofitClientRecentActions
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object RecentActionsRepository {

    val responseVal = MutableLiveData<Recentsidemenumodel>()

    private var Tag: String = " RecentActionsRepository "

    fun getRecentActions(baseUrl:String): MutableLiveData<Recentsidemenumodel> {

        RetrofitClientRecentActions.BASE_URL =baseUrl
      //  Log.d(Tag, " baseurl $baseUrl")

        Log.d(Tag, " RetrofitClientRecentActions ${RetrofitClientRecentActions.BASE_URL}")
        val call = RetrofitClientRecentActions.apiInterface.getSidemenu(baseUrl)
        call.enqueue(object : Callback<Recentsidemenumodel> {

            override fun onFailure(call: Call<Recentsidemenumodel>, t: Throwable) {
                // TODO("Not yet implemented")
                Log.d("$Tag onFailure ", t.message.toString())
                responseVal.value = Recentsidemenumodel(null)

            }

            override fun onResponse(
                call: Call<Recentsidemenumodel>,
                response: Response<Recentsidemenumodel>
            ) {

                var message = response.message()

                Log.d(Tag, "message is $message")

                // TODO("Not yet implemented")
                Log.d("onResponse : ", response.body().toString())
                if (response.body() != null) {

                    val data = response.body()!!.data
                 //   val msg = data!!.accessInfo
                    responseVal.value = Recentsidemenumodel(data)

                }

                else{
                    responseVal.value = Recentsidemenumodel(null)
                }




            }
        })

        return responseVal
    }

}