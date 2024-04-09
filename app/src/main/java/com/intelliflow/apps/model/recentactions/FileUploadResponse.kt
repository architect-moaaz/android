package com.intelliflow.apps.model.recentactions

import com.google.gson.annotations.SerializedName


data class FileUploadResponse (

  @SerializedName("file" ) var file : File? = File()

)


data class File (

  @SerializedName("fieldname"    ) var fieldname    : String? = null,
  @SerializedName("originalname" ) var originalname : String? = null,
  @SerializedName("encoding"     ) var encoding     : String? = null,
  @SerializedName("mimetype"     ) var mimetype     : String? = null,
  @SerializedName("id"           ) var id           : String? = null,
  @SerializedName("filename"     ) var filename     : String? = null,
  @SerializedName("metadata"     ) var metadata     : String? = null,
  @SerializedName("bucketName"   ) var bucketName   : String? = null,
  @SerializedName("chunkSize"    ) var chunkSize    : Int?    = null,
  @SerializedName("size"         ) var size         : Int?    = null,
  @SerializedName("md5"          ) var md5          : String? = null,
  @SerializedName("uploadDate"   ) var uploadDate   : String? = null,
  @SerializedName("contentType"  ) var contentType  : String? = null

)