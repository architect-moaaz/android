package com.intelliflow.apps.adapter

import android.database.DataSetObserver
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import android.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.intelliflow.apps.R
import com.intelliflow.apps.data.model.MyApps
import com.intelliflow.apps.databinding.AppsListBinding


 class MainAdapter: RecyclerView.Adapter<MainViewHolder>(), ListAdapter {

    var appsData : MyApps? = null

    fun setApps(apps: MyApps) {

        this.appsData = apps
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = AppsListBinding.inflate(inflater, parent, false)
        return MainViewHolder(binding)

    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
      val appName=  appsData?.data?.apps?.get(position)
       // val movie = apps[0].data.apps
       holder.binding.appName.text = appName?.app
       // holder.binding.appName2.text = appName?.app
        if (appName != null) {
            var workSpace=appName.workspace

          //  Glide.with(holder.itemView.context).load(R.drawable.appicon).into(holder.binding.imageview)
        }

    }

    override fun getItemCount(): Int {
        return appsData?.data?.apps?.size ?: 0
    }

    override fun registerDataSetObserver(p0: DataSetObserver?) {
        TODO("Not yet implemented")
    }

    override fun unregisterDataSetObserver(p0: DataSetObserver?) {
        TODO("Not yet implemented")
    }

    override fun getCount(): Int {
      return  appsData?.data?.apps?.size ?: 0
    }

    override fun getItem(p0: Int): Any {
        TODO("Not yet implemented")
    }

     override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
         TODO("Not yet implemented")
     }

     override fun getViewTypeCount(): Int {
         TODO("Not yet implemented")
     }

     override fun isEmpty(): Boolean {
         TODO("Not yet implemented")
     }

     override fun areAllItemsEnabled(): Boolean {
         TODO("Not yet implemented")
     }

     override fun isEnabled(p0: Int): Boolean {
         TODO("Not yet implemented")
     }

     // in below function we are getting individual item of grid view.
//    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
//        var convertView = convertView
//        // on blow line we are checking if layout inflater
//        // is null, if it is null we are initializing it.
//        if (layoutInflater == null) {
//            layoutInflater =
//                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
//        }
//        // on the below line we are checking if convert view is null.
//        // If it is null we are initializing it.
//        if (convertView == null) {
//            // on below line we are passing the layout file
//            // which we have to inflate for each item of grid view.
//            convertView = layoutInflater!!.inflate(R.layout.gridview_item, null)
//        }
//        // on below line we are initializing our course image view
//        // and course text view with their ids.
//        courseIV = convertView!!.findViewById(R.id.idIVCourse)
//        courseTV = convertView!!.findViewById(R.id.idTVCourse)
//        // on below line we are setting image for our course image view.
//        courseIV.setImageResource(courseList.get(position).courseImg)
//        // on below line we are setting text in our course text view.
//        courseTV.setText(courseList.get(position).courseName)
//
//        val appName=  appsData?.data?.apps?.get(position)
//        // val movie = apps[0].data.apps
//        holder.binding.appName.text = appName?.app
//        holder.binding.appName2.text = appName?.app
//        if (appName != null) {
//            var workSpace=appName.workspace
//
//            //  Glide.with(holder.itemView.context).load(R.drawable.appicon).into(holder.binding.imageview)
//        }
//
//
//
//        // at last we are returning our convert view.
//        return convertView
//    }

}

class MainViewHolder(val binding: AppsListBinding) : RecyclerView.ViewHolder(binding.root) {

}
