package com.intelliflow.apps.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.intelliflow.apps.model.AccessInfo
import com.intelliflow.apps.model.ServicesSetterGetter
import com.intelliflow.apps.model.SignInBody
import com.intelliflow.apps.model.forgetpassword.ForgetPasswordBody
import com.intelliflow.apps.model.forgetpassword.ForgetPasswordModel
import com.intelliflow.apps.repository.ForgetPasswordActivityRepository
import com.intelliflow.apps.repository.MainActivityRepository
import com.intelliflow.apps.view.password.ForgotPassword
import kotlin.math.sign


class ForgetPasswordViewModel : ViewModel() {

    var servicesLiveData: MutableLiveData<ForgetPasswordModel>? = null

    fun forgetPasswordModel(forgetPasswordBody: ForgetPasswordBody) : LiveData<ForgetPasswordModel>? {
        servicesLiveData = ForgetPasswordActivityRepository.getForgetPasswordModelCall(forgetPasswordBody)
        return servicesLiveData
    }

}