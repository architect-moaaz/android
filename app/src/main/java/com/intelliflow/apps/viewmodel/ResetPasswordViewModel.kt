package com.intelliflow.apps.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.intelliflow.apps.model.forgetpassword.ResetPasswordModel
import com.intelliflow.apps.repository.ResetPasswordActivityRepository
import com.intelliflow.apps.view.password.ForgotPassword
import kotlin.math.sign


class ResetPasswordViewModel : ViewModel() {

    var servicesLiveData: MutableLiveData<ResetPasswordModel>? = null

    fun forgetPasswordModel() : LiveData<ResetPasswordModel>? {
        servicesLiveData = ResetPasswordActivityRepository.resetPasswordModelCall()
        return servicesLiveData
    }

}