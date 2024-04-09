package com.intelliflow.apps.view.dashboard.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayout
import com.intelliflow.apps.R
import com.intelliflow.apps.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

  var  tabLayout: TabLayout?=null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root



//        val firstFragment=DashBoardFragment()
////        val secondFragment=SecondFragment()
////        val thirdFragment=ThirdFragment()
//
//        setCurrentFragment(firstFragment)
//       // root.findViewById<com.google.android.material.bottomnavigation.BottomNavigationView>()
//var bottomNavigationView = root.findViewById<com.google.android.material.bottomnavigation.BottomNavigationView>(R.id.bottomNavigationView)
//
//        bottomNavigationView.setOnItemSelectedListener {
//            when(it.itemId){
//                R.id.home->setCurrentFragment(firstFragment)
//                R.id.person->setCurrentFragment(firstFragment)
//                R.id.settings->setCurrentFragment(firstFragment)
//
//                else -> {
//                    setCurrentFragment(firstFragment)
//                }
//            }
//
//            true
//        }
//
//        bottomNavigationView.setOnNavigationItemSelectedListener {
////            when(it.itemId){
////                R.id.home->setCurrentFragment(firstFragment)
////                R.id.person->setCurrentFragment(firstFragment)
////                R.id.settings->setCurrentFragment(firstFragment)
////
////            }
//            true
//        }




        return root
    }

    private fun setCurrentFragment(fragment:Fragment): () -> FragmentTransaction = {
//        fragment.enterTransition.apply {
//
//            replace(R.id.flFragment,fragment)
//        }
        parentFragmentManager.beginTransaction().apply {
         //   replace(R.id.flFragment,fragment)
            commit()
        }
//        supportFragmentManager.beginTransaction().apply {
//
//            replace(R.id.flFragment,fragment)
//            commit()
       }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}