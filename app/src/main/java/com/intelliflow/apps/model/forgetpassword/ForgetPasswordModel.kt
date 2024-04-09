package com.intelliflow.apps.model.forgetpassword

import com.google.gson.annotations.SerializedName


data class ForgetPasswordModel (

  @SerializedName("status"  ) var status  : String?    = null,
  @SerializedName("message" ) var message : String? = null

)