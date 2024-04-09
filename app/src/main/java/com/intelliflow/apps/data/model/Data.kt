package com.intelliflow.apps.data.model

import com.google.gson.annotations.SerializedName


data class Data (

  @SerializedName("apps" ) var apps : ArrayList<Apps> = arrayListOf()

)