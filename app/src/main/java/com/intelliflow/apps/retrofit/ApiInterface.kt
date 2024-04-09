package com.intelliflow.apps.retrofit

import android.util.Log
import com.intelliflow.apps.data.model.MyApps
import com.intelliflow.apps.model.*
import com.intelliflow.apps.model.forgetpassword.ForgetPasswordBody
import com.intelliflow.apps.model.forgetpassword.ForgetPasswordModel
import com.intelliflow.apps.model.forgetpassword.ResetPasswordModel
import com.intelliflow.apps.model.recentactions.FileUploadResponse
import com.intelliflow.apps.model.recentactions.Recentsidemenumodel
import com.intelliflow.apps.model.recentactions.menuitem.RecentMenuItemModel
import com.intelliflow.apps.view.password.ForgotPassword
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.io.File
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.*
import javax.security.cert.CertificateException

interface ApiInterface {

    @Headers("Content-Type:application/json")
    @POST("Login")
    fun signin(@Body info: SignInBody): retrofit2.Call<LoginResponse>


    @GET("apps")
    fun getApps() : Call<MyApps>

    @Headers("Content-Type:application/json")
    @POST("forgot-password")
    fun forgetPassword(@Body forgetPasswordBody: ForgetPasswordBody) : Call<ForgetPasswordModel>

    @GET("fetchresetpassword")
    fun resetPassword() : Call<ResetPasswordModel>

    @GET
    fun getSidemenu(@Url url:String ) : Call<Recentsidemenumodel>

    @GET
    fun getSideMenuItemList(@Url url:String) : Call<List<RecentMenuItemModel>>

    @GET
    fun getSideMenuItemList1(@Url url:String) : Call<ResponseBody>

    @GET
    fun getAppsRecentActions(@Url url:String) : Call<AppsRecentActionsModel>

    @GET
    fun getAppsRecentActions1(@Url url:String, @Query("user")  user:String) : Call<AppsRecentActionsModel>

    @Headers("Content-Type:image/jpeg")
    @POST
    fun sendImageFile(@Url url:String, @Body  file: File) : Call<FileUploadResponse>

//    @Headers("Content-Type:application/json")
//    @POST("users")
//    fun registerUser(
//        @Body info: UserBody
//    ): retrofit2.Call<ResponseBody>
}

