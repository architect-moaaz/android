package com.intelliflow.apps.model.recentactions

import com.google.gson.annotations.SerializedName


data class Paths (

  @SerializedName("endpoint_label" ) var endpointLabel : String? = null,
  @SerializedName("path"           ) var path          : String? = null

)