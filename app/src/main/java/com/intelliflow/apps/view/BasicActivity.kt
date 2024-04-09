package com.intelliflow.apps.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.intelliflow.apps.R
import com.intelliflow.apps.databinding.ActivityBasicBinding
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController

class BasicActivity : AppCompatActivity() {

    lateinit var binding: ActivityBasicBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this,R.layout.activity_basic)
        setupNavigation()

    }

    private fun setupNavigation(){
        val navController = findNavController(R.id.myNavigationHostFragment)
       // setupActionBarWithNavController(navController,binding.drawerLayout)
        binding.navView.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(findNavController(R.id.myNavigationHostFragment),binding.drawerLayout)
    }
}