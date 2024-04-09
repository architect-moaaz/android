package com.intelliflow.apps.model.recentactions

import com.google.gson.annotations.SerializedName


data class Data (

  @SerializedName("_url"   ) var Url   : String?          = null,
  @SerializedName("_paths" ) var Paths : ArrayList<Paths> = arrayListOf()

)