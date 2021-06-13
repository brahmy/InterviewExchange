package com.asab.interviewexchange

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class AdapterNotifications(private val context: Context,private val notifications: List<NotificationDataModel>): RecyclerView.Adapter<AdapterNotifications.MyViewHolder>() {

    class MyViewHolder(view: View):RecyclerView.ViewHolder(view) {
        val textView_question: TextView
        val textView_link: TextView
        val imageView: ImageView

        init {
            textView_question=view.findViewById(R.id.id_notification_textView)
            imageView=view.findViewById(R.id.id_notification_imageView)
            textView_link=view.findViewById(R.id.id_notification_link)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AdapterNotifications.MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_notification, parent, false)

        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: AdapterNotifications.MyViewHolder, position: Int) {
        holder.textView_question.text=notifications.get(position).description
        if(!notifications.get(position).link.equals("empty",ignoreCase = true)) {
            holder.textView_link.visibility=View.VISIBLE
            holder.textView_link.text = notifications.get(position).link
        }else{
            holder.textView_link.visibility=View.GONE
        }

        Glide
            .with(context)
            .load(notifications.get(position).image)
            .placeholder(android.R.drawable.picture_frame)
            .into(holder.imageView)

    }

    override fun getItemCount(): Int {
        return notifications.size
    }
}