package com.intelliflow.apps.model.forgetpassword

import com.google.gson.annotations.SerializedName


data class ForgetPasswordBody (

  @SerializedName("username" ) var username : String? = null

)