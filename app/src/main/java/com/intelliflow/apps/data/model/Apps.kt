package com.intelliflow.apps.data.model

import com.google.gson.annotations.SerializedName


data class Apps (

  @SerializedName("workspace" ) var workspace : String? = null,
  @SerializedName("app"       ) var app       : String? = null

)