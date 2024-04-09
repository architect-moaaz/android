package com.intelliflow.apps.model

import com.google.gson.annotations.SerializedName

//data class SignInBody(val username: String,val password: String, )

data class SignInBody (

    @SerializedName("password" ) var password : String? = null,
    @SerializedName("username" ) var username : String? = null

)
