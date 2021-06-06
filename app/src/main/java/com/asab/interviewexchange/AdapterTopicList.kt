package com.asab.interviewexchange

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView

class AdapterTopicList(private var context: Context,private var listTopicList: List<String>):RecyclerView.Adapter<AdapterTopicList.MyViewHolder>() {

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView_name: TextView

        init {
            // Define click listener for the ViewHolder's View.
            textView_name = view.findViewById(R.id.id_textview_name)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AdapterTopicList.MyViewHolder {

        val view = LayoutInflater.from(context)
            .inflate(R.layout.adapter_home, parent, false)

        return AdapterTopicList.MyViewHolder(view).listen { position, type ->
            var bundle: Bundle = Bundle()
            bundle.putString("topic_name",listTopicList.get(position))
            bundle.putString("topic","Topic")
            Navigation.findNavController(view).navigate(R.id.TopicDetailsFragment,bundle);
        }
    }

    override fun onBindViewHolder(holder: AdapterTopicList.MyViewHolder, position: Int) {
        holder.textView_name.text = listTopicList.get(position)
    }

    override fun getItemCount(): Int {
        return listTopicList.size
    }
    fun <T : RecyclerView.ViewHolder> T.listen(event: (position: Int, type: Int) -> Unit): T {
        itemView.setOnClickListener {
            event.invoke(bindingAdapterPosition, itemViewType)
        }
        return this
    }

}