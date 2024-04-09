package com.intelliflow.apps.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.ContextWrapper

import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager

open class BaseActivity: AppCompatActivity() {

   @SuppressLint("RestrictedApi")
   override fun attachBaseContext(newBase: Context) {
// get chosen language from shread preference
//    val localeToSwitchTo = PreferenceManager(newBase).getAppLanguage()
//    val localeUpdatedContext: ContextWrapper = ContextUtils.updateLocale(newBase, localeToSwitchTo)
   // super.attachBaseContext(localeUpdatedContext)
}

}