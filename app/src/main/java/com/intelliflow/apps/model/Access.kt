package com.intelliflow.apps.model

import com.google.gson.annotations.SerializedName


data class Access (

  @SerializedName("manageGroupMembership" ) var manageGroupMembership : Boolean? = null,
  @SerializedName("view"                  ) var view                  : Boolean? = null,
  @SerializedName("mapRoles"              ) var mapRoles              : Boolean? = null,
  @SerializedName("impersonate"           ) var impersonate           : Boolean? = null,
  @SerializedName("manage"                ) var manage                : Boolean? = null

)