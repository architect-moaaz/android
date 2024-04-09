package com.intelliflow.apps.data.model

import com.google.gson.annotations.SerializedName


data class MyApps (

  @SerializedName("data" ) var data : Data? = Data()

)