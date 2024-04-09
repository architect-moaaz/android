package com.intelliflow.apps.model.recentactions

import com.google.gson.annotations.SerializedName


data class Tasks (

  @SerializedName("_id"         ) var Iid          : String?      = null,
  @SerializedName("id"          ) var id          : String?      = null,
  @SerializedName("information" ) var information : Information? = Information(),
  @SerializedName("app"         ) var app         : String?      = null,
  @SerializedName("first"       ) var first       : First?       = First(),
  @SerializedName("status"      ) var status      : String?      = null

)