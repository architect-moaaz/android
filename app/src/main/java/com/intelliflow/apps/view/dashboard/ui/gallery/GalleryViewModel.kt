package com.intelliflow.apps.view.dashboard.ui.gallery

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GalleryViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = " This is ToDo List "
    }
    val text: LiveData<String> = _text
}