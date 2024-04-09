package com.intelliflow.apps.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.intelliflow.apps.R
import com.intelliflow.apps.model.Notificationvar

class TodoAdapter  :
    RecyclerView.Adapter<TodoAdapter.ViewHolder>() {

    var dataList = emptyList<Notificationvar>()

    internal fun setDataList(dataList : List<Notificationvar>){
        this.dataList = dataList
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var image : ImageView
        var title : TextView

        init {
            image = itemView.findViewById(R.id.bell_ic)
            title = itemView.findViewById(R.id.notification_msg)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.todo_item, parent, false)
        return ViewHolder(view)
    }
    override fun getItemCount() = dataList.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var data = dataList[position]

        holder.title.text = data.notification_msg
        holder.image.setImageResource(data.bell_ic)
    }
}