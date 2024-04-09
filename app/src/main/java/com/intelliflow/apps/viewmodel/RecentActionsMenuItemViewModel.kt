package com.intelliflow.apps.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.intelliflow.apps.model.LoginResponse
import com.intelliflow.apps.model.ServicesSetterGetter
import com.intelliflow.apps.model.SignInBody
import com.intelliflow.apps.model.recentactions.FileUploadResponse
import com.intelliflow.apps.model.recentactions.menuitem.RecentMenuItemModel
import com.intelliflow.apps.repository.MainActivityRepository
import com.intelliflow.apps.repository.RecentActionsMenuItemRepository
import okhttp3.ResponseBody
import java.io.File
import kotlin.math.sign


class RecentActionsMenuItemViewModel : ViewModel() {

    var servicesLiveData: MutableLiveData<List<RecentMenuItemModel>?>? = null

    var fileUploadResponse: MutableLiveData<FileUploadResponse?>? = null

    var servicesLiveData1: MutableLiveData<ResponseBody>? = null

    fun sendFileData(baseUrl:String,file: File) : LiveData<FileUploadResponse?>? {
        fileUploadResponse = RecentActionsMenuItemRepository.sendImageFileRepository(baseUrl,file)
        return fileUploadResponse
    }

    fun getRecentMenuItem1(baseUrl:String) : LiveData<List<RecentMenuItemModel>?>? {
        servicesLiveData = RecentActionsMenuItemRepository.getRecentActionsMenuItem1(baseUrl)
        return servicesLiveData
    }

//    fun sendFileData(baseUrl:String) : LiveData<FileUploadResponse>? {
//     //   fileUploadResponse = RecentActionsMenuItemRepository.getRecentActionsMenuItem1(baseUrl)
//        return fileUploadResponse
//    }



}