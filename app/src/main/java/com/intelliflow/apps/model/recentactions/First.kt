package com.intelliflow.apps.model.recentactions

import com.google.gson.annotations.SerializedName


data class First (

  @SerializedName("id"               ) var id               : String? = null,
  @SerializedName("nodeId"           ) var nodeId           : String? = null,
  @SerializedName("nodeDefinitionId" ) var nodeDefinitionId : String? = null,
  @SerializedName("nodeName"         ) var nodeName         : String? = null,
  @SerializedName("nodeType"         ) var nodeType         : String? = null,
  @SerializedName("triggerTime"      ) var triggerTime      : String? = null,
  @SerializedName("leaveTime"        ) var leaveTime        : String? = null

)



//data class Data (
//
//  @SerializedName("count" ) var count : Int?             = null,
//  @SerializedName("tasks" ) var tasks : ArrayList<Tasks> = arrayListOf()
//
//)