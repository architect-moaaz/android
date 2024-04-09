package com.intelliflow.apps.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.intelliflow.apps.model.AppsRecentActionsModel
import com.intelliflow.apps.model.LoginResponse
import com.intelliflow.apps.model.ServicesSetterGetter
import com.intelliflow.apps.model.SignInBody
import com.intelliflow.apps.model.recentactions.Recentsidemenumodel
import com.intelliflow.apps.repository.AppsRecentActivityRepository
import com.intelliflow.apps.repository.MainActivityRepository
import com.intelliflow.apps.repository.RecentActionsRepository
import kotlin.math.sign


class RecentActionsViewModel : ViewModel() {

    var servicesLiveData: MutableLiveData<Recentsidemenumodel>? = null

    var appsLiveData: MutableLiveData<AppsRecentActionsModel>? = null
    fun getRecentActions(baseUrl:String) : LiveData<Recentsidemenumodel>? {
        servicesLiveData = RecentActionsRepository.getRecentActions(baseUrl)
        return servicesLiveData
    }

    fun getAppsRecentActions(baseUrl:String) : LiveData<AppsRecentActionsModel>? {
        appsLiveData = AppsRecentActivityRepository.getAppsRecentActions(baseUrl)
        return appsLiveData
    }

}