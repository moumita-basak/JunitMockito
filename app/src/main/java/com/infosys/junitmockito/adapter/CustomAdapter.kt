package com.infosys.junitmockito.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.infosys.junitmockito.R
import com.infosys.junitmockito.modelnew.ItemRow



class CustomAdapter(private val mContext: Context, private var itemsList: MutableList<ItemRow>) :
    RecyclerView.Adapter<CustomAdapter.MyViewHolder>() {
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var item_title: TextView = view.findViewById(R.id.item_title)
        var item_description: TextView = view.findViewById(R.id.item_description)
        var image_view: ImageView = view.findViewById(R.id.image_view)
        var image_arrow: ImageView = view.findViewById(R.id.image_arrow)
    }
    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.items, parent, false)
        return MyViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = itemsList[position]
        val title = item.title
        val description = item.description
        val image = item.imageHref

        try {
            if (!title.equals(null)){
                holder.item_title.text = item.title.toString()
            }
            if (!description.equals(null)) {
                holder.item_description.text = item.description.toString()
            }
            if (!image.equals(null)){
                Glide.with(mContext).asBitmap().load(image).into(holder.image_view)
            }

        }catch (ex:Exception){
            ex.printStackTrace()
        }


    }
    override fun getItemCount(): Int {
        return itemsList.size
    }



}