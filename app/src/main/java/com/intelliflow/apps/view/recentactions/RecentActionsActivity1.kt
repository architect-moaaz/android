package com.intelliflow.apps.view.recentactions


import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.contains
import androidx.core.view.iterator
import androidx.core.view.size
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.navigation.NavigationView
import com.intelliflow.apps.R
import com.intelliflow.apps.adapter.RecentActions_adapter
import com.intelliflow.apps.model.AppsRecentActionsModel
//import com.intelliflow.apps.model.RecentActionsModel
import com.intelliflow.apps.utils.ContextUtils
import com.intelliflow.apps.viewmodel.RecentActionsViewModel


private lateinit var recyclerView: RecyclerView
private lateinit var recentActionsAdapter: RecentActions_adapter
private var dataList = mutableListOf<AppsRecentActionsModel>()
private var mDrawerLayout: DrawerLayout? = null
private var mActionBarDrawerToggle: ActionBarDrawerToggle? = null
private lateinit var imageMenu: ImageView
private lateinit var appName: String
private lateinit var imageMenu1: ImageView

class RecentActionsActivity1 : AppCompatActivity(),
    NavigationView.OnNavigationItemSelectedListener {

    private val Tag: String = "RecentActionsActivity1"
    public var menuUrl: String? = null
    var navigationView: NavigationView? = null
    var tv_recentAction_appName : TextView? =null
    var progressBar : ProgressBar? =null


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recent_actions1)
        appName = intent.extras?.get("appname").toString()
        recyclerView = findViewById(R.id.action_recyclerView)
        imageMenu = findViewById(R.id.recent_image)
       // imageMenu1 = findViewById(R.id.recent_image1)
            progressBar =findViewById(R.id.recentActionProgress)
            progressBar?.isIndeterminate = true
            progressBar?.isClickable=false
            progressBar?.visibility=View.VISIBLE
            window?.setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        tv_recentAction_appName =findViewById(R.id.tv_recent_action_app)
        tv_recentAction_appName?.text = appName
        recyclerView.layoutManager = LinearLayoutManager(this)


      //  setRecentActionsData()

      //  recentActionsAdapter.setDataList(dataList)


        mDrawerLayout = findViewById<View>(R.id.drawerLayout) as DrawerLayout
        mActionBarDrawerToggle =
            ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close)
        mDrawerLayout!!.addDrawerListener(mActionBarDrawerToggle!!)
        mActionBarDrawerToggle!!.syncState()

        var toolbar = findViewById<View>(R.id.materialToolbar) as MaterialToolbar

        // using toolbar as ActionBar
        setSupportActionBar(toolbar) //set

        val actionbar: ActionBar? = supportActionBar //get

        actionbar!!.title = ""
        actionbar.setDisplayHomeAsUpEnabled(false)

        navigationView = findViewById<NavigationView>(R.id.nav_view)
        //   navigationView?.removeAllViews()
        navigationView?.setNavigationItemSelectedListener(this)
       // navigationView?.explicitStyle=
        imageMenu.setOnClickListener(View.OnClickListener {

            //  mDrawerLayout.openDrawer()
            if (!mDrawerLayout!!.isDrawerOpen(GravityCompat.START)) {
                mDrawerLayout!!.openDrawer(GravityCompat.START)
            } else {
                mDrawerLayout!!.closeDrawer(GravityCompat.START)
            }
        })


        var baseUrl =
            "https://ifapp.intelliflow.in/app-center/Intelliflow/" + appName + "/" + "context/"
        Log.d(Tag, "baseurl $baseUrl")
            if(ContextUtils.isOnline(this)) {
                progressBar?.visibility = View.VISIBLE
                window.setFlags(
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                )
                getRecentMenu(baseUrl)
                getAppsRecentActions("https://ifapp.intelliflow.in/app-center/app/user/activities/LeaveApplication/")
            }
            else{

                progressBar?.visibility=View.INVISIBLE
                window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)

                Toast.makeText(
                    this@RecentActionsActivity1, " You don't have Network Connection! ", Toast.LENGTH_SHORT
                ).show();

            }

      var preferences=this.getSharedPreferences("baseUrl",Context.MODE_PRIVATE)

      var urlEditor=  preferences.edit()

        urlEditor.putString("url",baseUrl)

        urlEditor.commit()

    }


    fun  getRecentMenu(baseUrl:String){

      var recentActionsViewModel = ViewModelProvider(this)[RecentActionsViewModel::class.java]

      recentActionsViewModel.getRecentActions(baseUrl!!)!!

          .observe(this, Observer { recentActions ->

              progressBar?.visibility=View.GONE
              window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)

              val paths = recentActions.data?.Paths
              menuUrl = recentActions.data?.Url
              Log.d(Tag, "paths is $paths")

              if (paths != null) {
                  var size = 0;
                  for (path in paths) {
                      var pathis = path.path
                      Log.d(Tag, "pathis is $pathis")
                      val menu = navigationView?.menu
                      Log.d(Tag, "menu is $menu")
                      //    menu?.size
                      if (size == 0) {
                          menu?.clear()
                      }
                      menu?.add(pathis)
                      Log.d(Tag, "menu size ${menu?.size}")
                    //  navigationView?.invalidate()
                      size++
                  }
              } else {
                //  navigationView?.menu?.clear()
                  Toast.makeText(this, " Unable to get the Data ", Toast.LENGTH_LONG)
                      .show()
              }
          }

          )
    }


    fun  getAppsRecentActions(baseUrl:String){

        var recentActionsViewModel = ViewModelProvider(this)[RecentActionsViewModel::class.java]

        recentActionsViewModel.getAppsRecentActions(baseUrl!!)!!
            .observe(this, Observer { recentActions ->

                progressBar?.visibility=View.GONE
                window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                Log.d(Tag, "RecentActions $recentActions")
                recentActionsAdapter = RecentActions_adapter()
                recentActionsAdapter.dataList= recentActions.data?.tasks!!
                recyclerView.adapter = recentActionsAdapter

            }

            )

    }


    override fun onResume() {
        super.onResume()
    }


    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
    }


    private fun  setRecentActionsData(){
       // dataList.add(
//            AppsRecentActionsModel(
//                "Applied For Casual Leave",
//                "20-04-2022, 5:00 pm",
//                "Jaxcon schilfer",
//                "Approved"
//            )
    //    )


    }


    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        mActionBarDrawerToggle!!.syncState()
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        val id: Int = item.itemId
        if (id == R.id.nav_home) {
            // Handle the camera action
        }
        navigationView?.invalidate()
        var title = item.title
        var intent = Intent(this@RecentActionsActivity1, RecentMenuItemActivity::class.java)
        intent.putExtra("title", title)
        intent.putExtra("menuUrl", menuUrl)
        startActivity(intent)
        mDrawerLayout?.closeDrawer(GravityCompat.START)
        return true
    }

}