package com.asab.interviewexchange

import android.content.Context
import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView

class AdapterHome(private val context: Context, private val listHomeTopics:List<String>):
    RecyclerView.Adapter<AdapterHome.MyViewHolder>() {

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView_name: TextView

        init {
            // Define click listener for the ViewHolder's View.
            textView_name = view.findViewById(R.id.id_textview_name)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterHome.MyViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.adapter_home, parent, false)

        return MyViewHolder(view).listen { position, type ->
            var bundle: Bundle = Bundle()
            bundle.putString("app_info",listHomeTopics.get(position))
            Navigation.findNavController(view).navigate(R.id.LanguageFragment,bundle);
        }
    }

    override fun onBindViewHolder(holder: AdapterHome.MyViewHolder, position: Int) {
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        holder.textView_name.text = listHomeTopics.get(position)
    }

    override fun getItemCount(): Int {
        return listHomeTopics.size
    }
    fun <T : RecyclerView.ViewHolder> T.listen(event: (position: Int, type: Int) -> Unit): T {
        itemView.setOnClickListener {
            event.invoke(bindingAdapterPosition, itemViewType)
        }
        return this
    }

}