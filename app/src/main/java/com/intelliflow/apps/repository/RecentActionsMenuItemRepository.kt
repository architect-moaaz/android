package com.intelliflow.apps.repository


import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.gson.GsonBuilder
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import com.intelliflow.apps.model.recentactions.FileUploadResponse
import com.intelliflow.apps.model.recentactions.menuitem.RecentMenuItemModel
import com.intelliflow.apps.retrofit.RetrofitClientRecentMenuItem
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.util.*
import kotlin.collections.ArrayList

object RecentActionsMenuItemRepository {

    val responseVal = MutableLiveData<List<RecentMenuItemModel>?>()

    private val responseVal1 = MutableLiveData<FileUploadResponse?>()

    private var Tag: String = "RecentActionsMenuItemRepository"

    // var recentList = List<RecentMenuItemModel>()

    fun sendImageFileRepository(baseUrl: String, file: File): MutableLiveData<FileUploadResponse?>? {

       // RetrofitClientRecentMenuItem.BASE_URL = baseUrl

      //  Log.d(Tag, "baseUrl $baseUrl")

        val call = RetrofitClientRecentMenuItem.apiInterface.sendImageFile(baseUrl,file)

        call.enqueue(object : Callback<FileUploadResponse> {

            override fun onFailure(call: Call<FileUploadResponse>, t: Throwable) {
                //  // TODO("Not yet implemented")
                Log.d("$Tag onFailure : ", t.message.toString())

                responseVal.value = null
            }

            override fun onResponse(
                call: Call<FileUploadResponse>,
                response: Response<FileUploadResponse>
            ) {

                var message = response.body()

                Log.d(Tag, "fileupload response is $message")

                if (message != null) {

                    responseVal1.value = response.body()

                } else {
                    responseVal1.value= null
                }

            }
        })

        return responseVal1

    }


    fun getRecentActionsMenuItem1(baseUrl: String): MutableLiveData<List<RecentMenuItemModel>?> {

        //    MutableLiveData<ResponseBody> {

        RetrofitClientRecentMenuItem.BASE_URL = baseUrl
        Log.d(Tag, "baseUrl $baseUrl")
        val call = RetrofitClientRecentMenuItem.apiInterface.getSideMenuItemList1(baseUrl)
        call.enqueue(object : Callback<ResponseBody> {

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                // TODO("Not yet implemented")
                Log.d("onFailure : ", t.message.toString())

                responseVal.value = null
            }

            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {
                responseVal.value = null
              //  https://ifapp.intelliflow.in/q/Intelliflow/Dropdown/service/form/content/default/

            //    if(response.body()?)

                var responseData = response.body()?.string() ?: return
                val gsonParser =
                    GsonBuilder().enableComplexMapKeySerialization().serializeNulls().setLenient()
                        .create()
                val jsonElement = kotlin.runCatching {
                    gsonParser.fromJson(responseData, JsonObject::class.java)
                }.recoverCatching {
                    gsonParser.fromJson(responseData, JsonArray::class.java)
                }.getOrNull() ?: return
                val formData = if (jsonElement.isJsonObject) {
                    jsonElement.asJsonObject.get("formData") as JsonArray
                } else if (jsonElement.isJsonArray) {
                    jsonElement.asJsonArray
                } else {
                    responseVal.value = null
                    null
                }

                formData ?: return

                val formDataList =
                    gsonParser.fromJson<List<RecentMenuItemModel>>(formData.toString(),
                        object : TypeToken<List<RecentMenuItemModel>>() {

                        }.type
                    )

                responseVal.value = formDataList


//                if (responseBody != null) {
//
//
//                  //  val dataList =.fr
//                    var formDataStr = formData.toString()
//
//                    if (formDataStr.contains("formData")) {
//
//                        var formDataArray = formData.getJSONArray("formData")
//
//
//                        if (formDataArray != null) {
//
//                            // responseVal.value=
//
//
//                            var list: ArrayList<RecentMenuItemModel> = ArrayList()
//                            //Iterating JSON array
//                            for (i in 0 until formDataArray.length()) {
//
//                                var recentMenuItemModel: RecentMenuItemModel =
//                                    formDataArray[i] as RecentMenuItemModel
//                                list.add(recentMenuItemModel)
//                            }
//
//                            responseVal.value =list
//                        }
//
//                        // responseVal.value =
//                        //   var gsonList= gson.fromJson(gsontojson, Array<RecentMenuItemModel>::class.java).toList()
//
//                     //   Log.d(Tag, " gsonList $gsontojson")
//
//
//                    } else {
//
//                        val gson = GsonBuilder().create()
//
//                        var gsonList = gson.toJson(formData)
//
//                        responseVal.value =
//                            gson.fromJson(gsonList, Array<RecentMenuItemModel>::class.java).toList()
//
//
//                    }
//
//                } else {
//                    Log.d(Tag, "responseBody is null $responseBody")
//                }


                //}

            }
        })

        return responseVal
    }


}