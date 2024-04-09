package com.intelliflow.apps.model.forgetpassword

import com.google.gson.annotations.SerializedName


data class ResetPasswordModel (

  @SerializedName("status"  ) var status  : Int?    = null,
  @SerializedName("message" ) var message : String? = null

)