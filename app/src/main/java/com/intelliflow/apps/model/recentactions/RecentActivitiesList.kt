package com.intelliflow.apps.model.recentactions

import com.google.gson.annotations.SerializedName


data class RecentActivitiesList (

  @SerializedName("message" ) var message : String? = null,
  @SerializedName("data"    ) var data    : Data?   = Data()

)