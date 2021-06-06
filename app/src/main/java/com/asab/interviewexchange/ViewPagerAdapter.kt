package com.asab.interviewexchange

import android.content.Context
import android.graphics.Paint
import android.os.Bundle
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ViewPagerAdapter(private val context: Context, private val listImages:List<String>):
    RecyclerView.Adapter<ViewPagerAdapter.MyViewHolder>() {

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView

        init {
            // Define click listener for the ViewHolder's View.
            imageView = view.findViewById(R.id.imageViewMain)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerAdapter.MyViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.adapter_topic_images, parent, false)

        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewPagerAdapter.MyViewHolder, position: Int) {
        // Get element from your dataset at this position and replace the
        // contents of the view with that element

        println("imagehere:"+listImages.get(position).trim())
        Glide.with(context)
            .load(listImages.get(position))
            .placeholder(android.R.drawable.picture_frame)
            .error(android.R.drawable.stat_notify_error)
            .into(holder.imageView)

    }

    override fun getItemCount(): Int {
        return listImages.size
    }
    fun <T : RecyclerView.ViewHolder> T.listen(event: (position: Int, type: Int) -> Unit): T {
        itemView.setOnClickListener {
            event.invoke(bindingAdapterPosition, itemViewType)
        }
        return this
    }

}