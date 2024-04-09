package com.intelliflow.apps.view.dashboard

import android.annotation.SuppressLint
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.intelliflow.apps.R
import com.intelliflow.apps.utils.ContextUtils
import com.intelliflow.apps.view.*
import com.intelliflow.apps.view.dashboard.ui.home.DashBoardFragment
import com.intelliflow.apps.view.login.LoginActivity
import com.intelliflow.apps.view.settings.Notification_Settings
import java.util.*

class Dashboard : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    lateinit var toggle: ActionBarDrawerToggle
    private val dashboardHome = DashBoardFragment()

    private val todo = TodoFragment()
    private val notification = Notification()
    private var isUserLoggedIn : Boolean?=false
    private lateinit var logoutBtn: Button
    private var Tag : String = " Dashboard "
    var firstName : String? =null
    var lastName : String? =null
    var useremail : String? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dash_board)

        replaceFragment(dashboardHome, "")

        val   sharedPreference =  getSharedPreferences("emailSave", Context.MODE_PRIVATE)

        isUserLoggedIn=  sharedPreference.getBoolean("isLoggedin",false)

        Log.d(Tag, " isUserLoggedIn $isUserLoggedIn")

      firstName= sharedPreference.getString("firstName","").toString()
      lastName= sharedPreference.getString("lastName","").toString()
        useremail= sharedPreference.getString("email","").toString()

        val bottom_navigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        bottom_navigation.itemIconTintList = null;
//        val notify_badge = bottom_navigation.getOrCreateBadge(R.id.nav_slideshow)
//        notify_badge.isVisible = true
//        notify_badge.number = 4
//        val todo_badge = bottom_navigation.getOrCreateBadge(R.id.nav_gallery)
//        todo_badge.isVisible = true
//        todo_badge.number = 4



        bottom_navigation.setOnNavigationItemSelectedListener {
            it.isChecked=true
            when(it.itemId){

                R.id.nav_home -> replaceFragment(dashboardHome, it.title.toString())
                R.id.nav_gallery -> replaceFragment(todo, it.title.toString())
                R.id.nav_slideshow -> replaceFragment(notification, it.title.toString())

            }
            true
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window?.statusBarColor = getColor(R.color.colorPrimary)
        }

        setUpViews()


    }

    override fun attachBaseContext(newBase: Context?) {
        val localeToSwitchTo = Locale("en")
        val localeUpdatedContext: ContextWrapper? =
            newBase?.let { ContextUtils.updateLocale(it, localeToSwitchTo) }
        super.attachBaseContext(localeUpdatedContext)
    }

    private fun replaceFragment(fragment: Fragment, title:String){
        if (fragment != null){
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.dashboard_container,fragment)
            transaction.commit()
            setTitle("")
        }
    }

    private fun setUpViews(){
        setUpDrawerLayout()
    }

    @SuppressLint("SetTextI18n")
    private fun setUpDrawerLayout(){
        val appBar = findViewById<MaterialToolbar>(R.id.appBar)
        setSupportActionBar(appBar)
        drawerLayout = findViewById(R.id.drawerLayout)
      //  drawerLayout

        toggle = ActionBarDrawerToggle(this, drawerLayout,R.string.open, R.string.close)
        // navView.setupWithNavController(navController)
         drawerLayout.addDrawerListener(toggle)
         toggle.syncState()

     //   supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        supportActionBar?.apply {
//            setDisplayShowHomeEnabled(true)
//        }
        supportActionBar?.apply {
            setDisplayShowHomeEnabled(true)
           setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.hamburger)
        }
    //    supportActionBar?.
        val navView : NavigationView = findViewById(R.id.nav_view)
        val header: View = navView.getHeaderView(0)
        var name =header.findViewById(R.id.nametv) as TextView
        var  email = header.findViewById(R.id.header_email) as TextView
        name.text=(firstName+" "+lastName)
        email.text=(useremail)
        navView.setNavigationItemSelectedListener {
            it.isChecked=true

            when(it.itemId){
                R.id.notificationSettings -> replaceSubFragment(Notification_Settings(), it.title.toString())
                R.id.termsuse -> replaceSubFragment(TermsOfUse(), it.title.toString())
                R.id.feedback -> {
                 //   replaceSubFragment(Feedbacks(), it.title.toString())

                    val intent = Intent(Intent.ACTION_SENDTO)
                    // intent.data = Uri.parse("mailto:customer-care@intelliflow.io")
                    intent.type="text/plain"
                    intent.putExtra(android.content.Intent.EXTRA_EMAIL, Array<String>(1){  "customer-care@intelliflow.io"})
                    //  intent.putExtra(Intent.EXTRA_EMAIL, "customer-care@intelliflow.io")
                    intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Feedback From Intelliflow Android APP")
                    intent.putExtra(android.content.Intent.EXTRA_TEXT, "Hi , ")
                    intent.type = "message/rfc822"
                    startActivity(Intent.createChooser(intent, "Select email"))
                }


                R.id.legal -> replaceSubFragment(PrivacyPolicy(), it.title.toString())
            }
            true
        }

          logoutBtn =navView.findViewById(R.id.nav_logout)
        logoutBtn.setOnClickListener{

           // Toast.makeText(this,"clicked on logout ",Toast.LENGTH_LONG).show()

            var sharedPreference = getSharedPreferences("emailSave", Context.MODE_PRIVATE)
            val editor = sharedPreference?.edit()
            editor?.putBoolean("isLoggedin", false)
            editor?.commit()
            startActivity( Intent(this, LoginActivity::class.java))
            finish()

        }

    }

    private fun replaceSubFragment(fragment: Fragment, title:String){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.dashboard_container,fragment)
        fragmentTransaction.commit()
        drawerLayout.closeDrawers()
        setTitle(title)
    }

    override fun onBackPressed() {
        super.onBackPressed()

       if(isUserLoggedIn == true){
         finish()
       }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.dash_board, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    }
