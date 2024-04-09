package com.intelliflow.apps.model

import com.google.gson.annotations.SerializedName

data class LoginResponse (

  @SerializedName("access_info" ) var accessInfo : AccessInfo?         = AccessInfo(),
  @SerializedName("UserInfo"    ) var UserInfo   : ArrayList<UserInfo>? = arrayListOf()

)