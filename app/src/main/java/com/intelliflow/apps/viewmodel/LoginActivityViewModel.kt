package com.intelliflow.apps.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.intelliflow.apps.model.LoginResponse
import com.intelliflow.apps.model.ServicesSetterGetter
import com.intelliflow.apps.model.SignInBody
import com.intelliflow.apps.repository.MainActivityRepository
import kotlin.math.sign


class LoginActivityViewModel : ViewModel() {

    var servicesLiveData: MutableLiveData<LoginResponse>? = null

    fun getUser(signInfo:SignInBody) : LiveData<LoginResponse>? {
        servicesLiveData = MainActivityRepository.getServicesApiCall(signInfo)
        return servicesLiveData
    }

}