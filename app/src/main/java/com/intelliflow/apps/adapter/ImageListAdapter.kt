package com.intelliflow.apps.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.intelliflow.apps.R
import com.intelliflow.apps.data.model.MyApps
import com.intelliflow.apps.databinding.AppsListBinding


internal class ImageListAdapter internal constructor(
    context: Context,
    private val resource: Int,
    private val itemList:MyApps
) : ArrayAdapter<ImageListAdapter.ItemViewHolder>(context, resource) {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private lateinit var itemBinding: AppsListBinding

    override fun getCount(): Int {

        return if (this.itemList != null) this.itemList.data?.apps?.size?.toInt()!! else 0

    }

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {

        var convertView = view
        val holder: ItemViewHolder

        if (convertView == null) {
            itemBinding = AppsListBinding.inflate(inflater)
            convertView = itemBinding.root
            holder = ItemViewHolder()
            holder.name = itemBinding.appName
            holder.icon = itemBinding.imageview
            convertView.tag = holder
        } else {
            holder = convertView.tag as ItemViewHolder
        }
      //  holder.icon?.setOnClickListener()
        holder.name!!.text = this.itemList.data?.apps?.get(position)?.app.toString()
     //   holder.icon!!.setImageResource(R.mipmap.ic_launcher)
        return convertView

    }

    internal class ItemViewHolder {
        var name: TextView? = null
        var icon: ImageView? = null
    }
}
