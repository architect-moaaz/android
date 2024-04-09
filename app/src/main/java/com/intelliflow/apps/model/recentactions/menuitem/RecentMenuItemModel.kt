package com.intelliflow.apps.model.recentactions.menuitem

import com.google.gson.annotations.SerializedName


data class RecentMenuItemModel (
//  var elementType = menu.elementType
//                                     var fieldTYpe = menu.fieldType
//                                     var required = menu.required
//                                     var edit = menu.edit
//                                     var multiSelect = menu.multiSelect
//                                     var fieldName = menu.fieldName
//                                     var choices: ArrayList<Choices> = menu.choices
//                                     var menuId= menu.id



 // @SerializedName("y"                    ) var y                    : Int?                = null,
 // @SerializedName("x"                    ) var x                    : Int?                = null,
  //@SerializedName("w"                    ) var w                    : Int?                = null,
//  @SerializedName("h"                    ) var h                    : Int?                = null,
//  @SerializedName("i"                    ) var i                    : Int?                = null,
  @SerializedName("id"                   ) var id                   : Int?                = null,
  @SerializedName("elementType"          ) var elementType          : String?             = null,
  @SerializedName("fieldType"            ) var fieldType            : String?             = null,
  @SerializedName("placeholder"          ) var placeholder          : String?             = null,
  @SerializedName("required"             ) var required             : String?             = null,
  @SerializedName("edit"                 ) var edit                 : Boolean?            = null,
  @SerializedName("multiSelect"          ) var multiSelect          : Boolean?            = null,
//  @SerializedName("VAxis"                ) var VAxis                : Boolean?            = null,
//  @SerializedName("minChoices"           ) var minChoices           : String?             = null,
//  @SerializedName("maxChoices"           ) var maxChoices           : String?             = null,
  @SerializedName("choices"              ) var choices              : ArrayList<Choices>  = arrayListOf(),
  @SerializedName("fieldName"            ) var fieldName            : String?             = null,
//  @SerializedName("date"                 ) var date                 : String?             = null,
 // @SerializedName("accessibility"        ) var accessibility        : Accessibility?      = Accessibility(),
//  @SerializedName("prefix"               ) var prefix               : String?             = null,
//  @SerializedName("suffix"               ) var suffix               : String?             = null,
//  @SerializedName("dateFormat"           ) var dateFormat           : String?             = null,
//  @SerializedName("ratingType"           ) var ratingType           : String?             = null,
//  @SerializedName("ratingScale"          ) var ratingScale          : Int?                = null,
//  @SerializedName("rating"               ) var rating               : String?             = null,
  @SerializedName("fileType"             ) var fileType             : String?             = null,
//  @SerializedName("minFilesLimit"        ) var minFilesLimit        : String?             = null,
//  @SerializedName("maxFilesLimit"        ) var maxFilesLimit        : String?             = null,
//  @SerializedName("minFileSize"          ) var minFileSize          : String?             = null,
//  @SerializedName("maxFileSize"          ) var maxFileSize          : String?             = null,
//  @SerializedName("processVariableName"  ) var processVariableName  : String?             = null,
//  @SerializedName("minLength"            ) var minLength            : String?             = null,
//  @SerializedName("maxLength"            ) var maxLength            : String?             = null,
//  @SerializedName("dateRangeStart"       ) var dateRangeStart       : String?             = null,
//  @SerializedName("dateRangeEnd"         ) var dateRangeEnd         : String?             = null,
//  @SerializedName("processInputVariable" ) var processInputVariable : String?             = null,
 // @SerializedName("dataGridProperties"   ) var dataGridProperties   : DataGridProperties? = DataGridProperties(),
//  @SerializedName("moved"                ) var moved                : Boolean?            = null,
//  @SerializedName("static"               ) var static               : Boolean?            = null,
//  @SerializedName("toolTip"              ) var toolTip              : String?             = null

)

data class Choices (

  @SerializedName("label" ) var label : String? = null,
  @SerializedName("value" ) var value : String? = null,
  @SerializedName("id"    ) var id    : Int?    = null,
  @SerializedName("choice" ) var choice : String? = null,
  @SerializedName("key" ) var key : String? = null,
)

data class Accessibility (

  @SerializedName("writeUsers"  ) var writeUsers  : ArrayList<WriteUsers> = arrayListOf(),
  @SerializedName("writeGroups" ) var writeGroups : ArrayList<WriteGroups> = arrayListOf(),
 // @SerializedName("writeUsers"  ) var writeUsers  : ArrayList<String> = arrayListOf(),
  @SerializedName("readUsers"   ) var readUsers   : ArrayList<String> = arrayListOf(),
  @SerializedName("hideUsers"   ) var hideUsers   : ArrayList<String> = arrayListOf(),
 // @SerializedName("writeGroups" ) var writeGroups : ArrayList<String> = arrayListOf(),
  @SerializedName("readGroups"  ) var readGroups  : ArrayList<String> = arrayListOf(),
  @SerializedName("hideGroups"  ) var hideGroups  : ArrayList<String> = arrayListOf()

)

data class DataGridProperties (

  @SerializedName("dataModelName" ) var dataModelName : String?           = null,
  @SerializedName("cols"          ) var cols          : ArrayList<String> = arrayListOf()

)

data class WriteUsers (

  @SerializedName("username" ) var username : String? = null,
  @SerializedName("id"       ) var id       : String? = null

)

data class WriteGroups (

  @SerializedName("name" ) var name : String? = null,
  @SerializedName("id"   ) var id   : String? = null

)