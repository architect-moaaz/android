package com.intelliflow.apps.view.dashboard.ui.home

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.AdapterView.OnItemClickListener
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayout
import com.intelliflow.apps.R
import com.intelliflow.apps.adapter.ImageListAdapter
import com.intelliflow.apps.data.model.MyApps
import com.intelliflow.apps.databinding.DashboardFragmentBinding
import com.intelliflow.apps.utils.ContextUtils
import com.intelliflow.apps.view.recentactions.RecentActionsActivity1
import com.intelliflow.apps.viewmodel.DashBoardActivityViewModel


class DashBoardFragment : Fragment() {

     var Tag1: String= "DashBoardFragment"
     lateinit var dashBoardActivityViewModel: DashBoardActivityViewModel
     var  tabLayout: TabLayout?=null

    private var _binding: DashboardFragmentBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    var allApps: MyApps?=null

    var appsCountTv:TextView ?=null

    var progressBar : ProgressBar? =null

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this)[HomeViewModel::class.java]
      //  var root: View ?=null
   //  root=   inflater.inflate(R.layout.dashboard_fragment,container)
        _binding = DashboardFragmentBinding.inflate(inflater, container, false)
        val root: View = binding.root
        appsCountTv = _binding!!.dashboardID.appcountTv
        dashBoardActivityViewModel = ViewModelProvider(this)[DashBoardActivityViewModel::class.java]
        progressBar =_binding!!. dashBoardProgress
        progressBar?.isIndeterminate = true
        progressBar?.isClickable=false
        if(activity?.let { ContextUtils.isOnline(it) } == true) {
            progressBar?.visibility = View.VISIBLE
          activity?.window?.setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            )
        dashBoardActivityViewModel.getMiniApps()!!.observe(viewLifecycleOwner, Observer {
                apps ->
            progressBar?.visibility=View.GONE
           activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)

           if(apps!=null) {

              var appsSize= apps.data?.apps?.size

               appsCountTv!!.text = "$appsSize apps"

               allApps =apps
                    val adapter = context?.let { ImageListAdapter(it, R.layout.apps_list, apps) }

                      binding.miniAppsInclude.gridview.adapter = adapter

}
        })
        }
        else{

            progressBar?.visibility=View.INVISIBLE
            activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            Toast.makeText(
                activity, " You don't have Network Connection! ", Toast.LENGTH_SHORT
            ).show()

        }

        binding.miniAppsInclude.gridview.onItemClickListener =
            OnItemClickListener { parent, view, position, id ->

           //     Toast.makeText(context, " position is $position",Toast.LENGTH_LONG).show()

                var appName= allApps?.data?.apps?.get(position)?.app
                Log.d(Tag1, "appName $appName")
                var intent=Intent(context, RecentActionsActivity1::class.java)
                if(appName!=null)
                intent.putExtra("appname",appName)
                startActivity(intent)

               // Intent(this, RecentActionsActivity::class.java)
//                when (position) {
//                    0 -> {
//                        Toast.makeText(context, " position is $position",Toast.LENGTH_LONG).show()
//                    }
//                    1 -> {}
//                }
            }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()

    }

}