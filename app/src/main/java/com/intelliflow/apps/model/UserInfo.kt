package com.intelliflow.apps.model

import com.google.gson.annotations.SerializedName
import com.intelliflow.apps.model.Access


data class UserInfo (

  @SerializedName("id"               ) var id               : String?  = null,
  @SerializedName("createdTimestamp" ) var createdTimestamp : Long?     = null,
  @SerializedName("username"         ) var username         : String?  = null,
  @SerializedName("enabled"          ) var enabled          : Boolean? = null,
  @SerializedName("emailVerified"    ) var emailVerified    : Boolean? = null,
  @SerializedName("firstName"        ) var firstName        : String?  = null,
  @SerializedName("lastName"         ) var lastName         : String?  = null,
  @SerializedName("email"            ) var email            : String?  = null,
  @SerializedName("access"           ) var access           : Access?  = Access()

)