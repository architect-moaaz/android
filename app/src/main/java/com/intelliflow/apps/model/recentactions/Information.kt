package com.intelliflow.apps.model.recentactions

import com.google.gson.annotations.SerializedName


data class Information (

  @SerializedName("processName" ) var processName : String? = null,
  @SerializedName("startDate"   ) var startDate   : String? = null,
  @SerializedName("endDate"     ) var endDate     : String? = null

)