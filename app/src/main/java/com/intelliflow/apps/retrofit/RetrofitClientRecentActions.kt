package com.intelliflow.apps.retrofit

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.viewbinding.BuildConfig
import com.intelliflow.apps.view.recentactions.RecentActionsActivity1
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.*
import javax.security.cert.CertificateException

class RetrofitClientRecentActions {

  // var pref= .getSharedPreferences("baseUrl", Context.MODE_PRIVATE)

    companion object {

        private val TAG = "RetrofitClientRecentActions"

        var BASE_URL: String = ""

     //   https://ifapp.intelliflow.in/app-center/Intelliflow/LeaveApplication/context

        //  https://ifapp.intelliflow.in/app-center/Intelliflow/apps

        val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }

        val client: OkHttpClient = OkHttpClient.Builder().apply {
            this.addInterceptor(interceptor)
        }.build()

        @SuppressLint("LongLogTag")
        private fun getUnsafeOkHttpClient(): OkHttpClient.Builder? {
            Log.d(TAG, "BaseUrl ${RetrofitClientRecentActions.BASE_URL}");
            return try {
                // Create a trust manager that does not validate certificate chains
                val trustAllCerts: Array<TrustManager> = arrayOf<TrustManager>(
                    object : X509TrustManager {
                        @Throws(CertificateException::class)
                        override fun checkClientTrusted(
                            chain: Array<X509Certificate?>?,
                            authType: String?
                        ) {
                        }

                        @Throws(CertificateException::class)
                        override fun checkServerTrusted(
                            chain: Array<X509Certificate?>?,
                            authType: String?
                        ) {
                        }


                        override fun getAcceptedIssuers(): Array<X509Certificate> {
                            return arrayOf()
                        }

//                    val acceptedIssuers: Array<X509Certificate?>?
//                        get() = arrayOf()
                    }
                )

                // Install the all-trusting trust manager
                val sslContext: SSLContext = SSLContext.getInstance("SSL")
                sslContext.init(null, trustAllCerts, SecureRandom())

                // Create an ssl socket factory with our all-trusting manager
                val sslSocketFactory: SSLSocketFactory = sslContext.socketFactory

                val builder = OkHttpClient.Builder()

                builder.readTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
                    .connectTimeout(60, TimeUnit.SECONDS).build()
                // val builder = okHttpBuilder.readTimeout
                builder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
                builder.hostnameVerifier { hostname, session -> true }
                builder
            } catch (e: Exception) {
                throw RuntimeException(e)
            }
        }

        fun getRetrofitInstance(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(RetrofitClient.BASE_URL)
              //    .client(getUnsafeOkHttpClient()?.build())
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        val retrofitClient: Retrofit.Builder by lazy {

            val levelType: HttpLoggingInterceptor.Level
            if (BuildConfig.BUILD_TYPE.contentEquals("debug"))
                levelType = HttpLoggingInterceptor.Level.BODY else levelType =
                HttpLoggingInterceptor.Level.NONE

            val logging = HttpLoggingInterceptor()
            logging.level = levelType

            val okhttpClient = OkHttpClient.Builder()
            okhttpClient.addInterceptor(logging)

         //   val sharedPreferences: SharedPreferences = getSharedPreferences("baseUrl", Context.MODE_PRIVATE)


            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(getUnsafeOkHttpClient()?.build())
              //  .client(okhttpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
        }


        val apiInterface: ApiInterface by lazy {
            retrofitClient
                .build()
                .create(ApiInterface::class.java)
        }

    }


}