package com.intelliflow.apps.adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.intelliflow.apps.R
import com.intelliflow.apps.data.model.MyApps


class RecyclerViewAdapter( mcontext: Context) :
    RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder>() {
    // private val appsList: MyApps
    var appsData : MyApps? = null
    private val mcontext: Context
     lateinit var tvAppName: TextView
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        // Inflate Layout
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.apps_list, parent, false)
        tvAppName= view.findViewById(R.id.app_name)
        return RecyclerViewHolder(view)
    }


    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        // Set the data to textview and imageview.
    //    val recyclerData: RecyclerData = courseDataArrayList[position]

//        holder.courseTV.setText(recyclerData.getTitle())
//        holder.courseIV.setImageResource(recyclerData.getImgid())

        val appName=  appsData?.data?.apps?.get(position)
        if (appName != null) {
            tvAppName.text= appName.app
        }
        // val movie = apps[0].data.apps

      //  holder.tvAppName = appName?.app
      //  holder.binding.appName2.text = appName?.app
        if (appName != null) {
            var workSpace=appName.workspace

            //  Glide.with(holder.itemView.context).load(R.drawable.appicon).into(holder.binding.imageview)
        }



    }

    override fun getItemCount(): Int {
        // this method returns the size of recyclerview
        return appsData?.data?.apps?.size ?: 0
    }

    // View Holder Class to handle Recycler View.
    inner class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val appName: TextView


        init {
            appName = itemView.findViewById(R.id.app_name)

        }
    }

    init {
        //courseDataArrayList = appsData
        this.mcontext = mcontext
    }
}
