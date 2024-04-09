package com.intelliflow.apps.model


import com.google.gson.annotations.SerializedName


data class AppsRecentActionsModel (

    @SerializedName("message" ) var message : String? = null,
    @SerializedName("data"    ) var data    : Data?   = Data()

)

data class Information (

    @SerializedName("processName" ) var processName : String? = null,
    @SerializedName("startDate"   ) var startDate   : String? = null,
    @SerializedName("endDate"     ) var endDate     : String? = null

)

data class First (

    @SerializedName("id"               ) var id               : String? = null,
    @SerializedName("nodeId"           ) var nodeId           : String? = null,
    @SerializedName("nodeDefinitionId" ) var nodeDefinitionId : String? = null,
    @SerializedName("nodeName"         ) var nodeName         : String? = null,
    @SerializedName("nodeType"         ) var nodeType         : String? = null,
    @SerializedName("triggerTime"      ) var triggerTime      : String? = null,
    @SerializedName("leaveTime"        ) var leaveTime        : String? = null


)

data class Tasks (

    @SerializedName("_id"         ) var Iid          : String?      = null,
    @SerializedName("id"          ) var id          : String?      = null,
    @SerializedName("information" ) var information : Information? = Information(),
    @SerializedName("app"         ) var app         : String?      = null,
    @SerializedName("first"       ) var first       : First?       = First(),
    @SerializedName("status"      ) var status      : String?      = null

)


data class Data (

    @SerializedName("count" ) var count : Int?             = null,
    @SerializedName("tasks" ) var tasks : ArrayList<Tasks> = arrayListOf()

)