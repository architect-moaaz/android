package com.intelliflow.apps.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.intelliflow.apps.data.model.Apps
import com.intelliflow.apps.data.model.MyApps
import com.intelliflow.apps.repository.DashBoardActivityRepository



class DashBoardActivityViewModel : ViewModel() {

    var servicesLiveData: MutableLiveData<MyApps>? = null

    fun getMiniApps() : LiveData<MyApps>? {
        servicesLiveData = DashBoardActivityRepository.getServicesApiCall()
        return servicesLiveData
    }

}