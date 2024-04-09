package com.intelliflow.apps.model.recentactions.menuitem

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import java.lang.Exception
import java.lang.reflect.Type

sealed class RecentActionSampleResponse {
    data class ArraySampleResponse(val currentResponses: List<RecentMenuItemModel>) : RecentActionSampleResponse()
    data class SimpleSampleResponse(val currentResponse: RecentMenuItemModel) : RecentActionSampleResponse()
}

class CustomDeserializer : JsonDeserializer<RecentActionSampleResponse> {

    @Throws(JsonParseException::class)
    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext) : RecentActionSampleResponse{


        if (json.isJsonObject) {

            // parse as object and create your object
            return RecentActionSampleResponse.SimpleSampleResponse(json as RecentMenuItemModel)
        }
        else if (json.isJsonArray) {
            // parse as array and create your object
            return RecentActionSampleResponse.ArraySampleResponse(json as List<RecentMenuItemModel>)

        }
        else {
           throw Exception()
        }
    }
}