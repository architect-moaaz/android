package com.intelliflow.apps.view.dashboard


import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBarDrawerToggle
import com.google.android.material.navigation.NavigationView
import androidx.navigation.ui.AppBarConfiguration
// import androidx.navigation.ui.setupWithNavControllear
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.intelliflow.apps.view.Feedbacks
import com.intelliflow.apps.view.Notification
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.intelliflow.apps.R
import com.intelliflow.apps.databinding.ActivityDashBoardBinding
import com.intelliflow.apps.view.TermsOfUse
import com.intelliflow.apps.view.dashboard.ui.gallery.GalleryFragment
import com.intelliflow.apps.view.dashboard.ui.home.DashBoardFragment
import com.intelliflow.apps.view.dashboard.ui.slideshow.SlideshowFragment
import com.intelliflow.apps.view.login.LoginActivity
import com.intelliflow.apps.view.password.ForgotPassword
import com.intelliflow.apps.view.settings.Settings
import com.intelliflow.apps.viewmodel.DashBoardActivityViewModel


class DashBoardActivity : AppCompatActivity() {

    private val Tag: String="DashBoardActivity"
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityDashBoardBinding
    lateinit var dashBoardActivityViewModel: DashBoardActivityViewModel
  //  private val dashboardHome = dashboard_home()
    private val dashboardHome = DashBoardFragment()

    private val todo = GalleryFragment()
    private val notification = SlideshowFragment()
    lateinit var drawerLayout: DrawerLayout
    lateinit var toggle: ActionBarDrawerToggle

    lateinit var btn_logout : Button

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashBoardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        replaceFragment(dashboardHome, "Home")
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        navView.setOnClickListener{

        }

        btn_logout = navView.findViewById(R.id.nav_logout)
        btn_logout.setOnClickListener{



        }

        supportActionBar?.setDisplayShowTitleEnabled(false);
        supportActionBar?.setHomeButtonEnabled(true);
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.setHomeAsUpIndicator(R.drawable.hamburger);
        navView.itemIconTintList=null


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window?.statusBarColor = getColor(R.color.colorPrimary)
        }

        val navBottomView : BottomNavigationView = findViewById(R.id.bottom_navigation)
        appBarConfiguration = AppBarConfiguration(setOf(
            R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow), drawerLayout)
        navBottomView.itemIconTintList = null
        val menuItemId: Int = navBottomView.menu.getItem(1).itemId //0 menu item index.
        var   badgeDrawable = navBottomView.getOrCreateBadge(menuItemId)

        badgeDrawable.backgroundColor = resources.getColor(R.color.accentColor,null)
        badgeDrawable.isVisible = true
        badgeDrawable.number = 10
        badgeDrawable.badgeGravity = BadgeDrawable.TOP_END    //badge gravity
        val menuItemId1: Int = navBottomView.menu.getItem(2).itemId //0 menu item index.
        var   badgeDrawable1 = navBottomView.getOrCreateBadge(menuItemId1)
        badgeDrawable1.backgroundColor = resources.getColor(R.color.accentColor,null)
        badgeDrawable1.isVisible = true
        badgeDrawable1.number = 10
        badgeDrawable1.badgeGravity = BadgeDrawable.TOP_END


        dashBoardActivityViewModel = ViewModelProvider(this)[DashBoardActivityViewModel::class.java]


        dashBoardActivityViewModel.getMiniApps()!!.observe(this, Observer { apps ->



        })

        navBottomView.setOnNavigationItemSelectedListener {

            it.isChecked=true
            when(it.itemId){

                R.id.nav_home -> replaceFragment(dashboardHome, it.title.toString())
                R.id.nav_gallery -> replaceFragment(todo, it.title.toString())
                R.id.nav_slideshow -> replaceFragment(notification, it.title.toString())

            }
            true
        }




        setUpDrawerLayout()

    }

    private fun setUpDrawerLayout(){
        val appBar = findViewById<MaterialToolbar>(R.id.appBar)
        setSupportActionBar(appBar)
        drawerLayout = findViewById(R.id.drawerLayout)
        toggle = ActionBarDrawerToggle(this, drawerLayout,R.string.open, R.string.close)
        // navView.setupWithNavController(navController)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val navView : NavigationView = findViewById(R.id.nav_view)

        navView.setNavigationItemSelectedListener {
            it.isChecked=true

            when(it.itemId){

                R.id.notificationSettings -> replaceSubFragment(Notification(), it.title.toString())
                R.id.termsuse -> replaceSubFragment(TermsOfUse(), it.title.toString())
                R.id.feedback -> replaceSubFragment(Feedbacks(), it.title.toString())
                R.id.legal -> replaceSubFragment(Settings(), it.title.toString())

            }
            true
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.dash_board, menu)
        return true
    }

    private fun replaceFragment(fragment: Fragment, title:String){
        if (fragment != null){
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.dashboard_container,fragment)
            transaction.commit()
            setTitle(title)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.notificationSettings) {

            return true
        }
        return super.onOptionsItemSelected(item)
    }

//    override fun onSupportNavigateUp(): Boolean {
//      //  val navController = findNavController(R.id.nav_host_fragment_content_dash_board)
//        return   super.onSupportNavigateUp()
//   //  return   navController.navigateUp(appBarConfiguration) ||   super.onSupportNavigateUp()
//
//
//    }


}