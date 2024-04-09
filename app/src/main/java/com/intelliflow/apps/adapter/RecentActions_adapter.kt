package com.intelliflow.apps.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView
import com.intelliflow.apps.R

import com.intelliflow.apps.model.AppsRecentActionsModel
import com.intelliflow.apps.model.Tasks

class RecentActions_adapter  :
    RecyclerView.Adapter<RecentActions_adapter.ViewHolder>() {

    var dataList = emptyList<Tasks>()

    internal fun setDataList(dataList : List<Tasks>){
        this.dataList = dataList
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        var title : TextView
        var time : TextView
         var name : TextView
        var status : TextView

//
        init {

            title = itemView.findViewById(R.id.title_action)
            time = itemView.findViewById(R.id.recent_time)
            name = itemView.findViewById(R.id.recent_name)
            status = itemView.findViewById(R.id.recent_status)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recent_actions_item, parent, false)
        return ViewHolder(view)
    }
    override fun getItemCount() = dataList.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        var data = dataList[position]?.information

        holder.title.text = data?.processName
        holder.time.text = data?.startDate
        holder.name.text = data?.processName
        holder.status.text = dataList[position]?.status

    }
}