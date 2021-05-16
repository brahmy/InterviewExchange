package com.asab.interviewexchange

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide

data class AdapterChooseLanguage(private var languageList:List<String>, private val context: Context) : BaseAdapter(){

    override fun getItem(position: Int): Any {
        return languageList.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return languageList.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = View.inflate(context,R.layout.adapter_choose_language,null)

        val textView_language_name = view.findViewById<TextView>(R.id.textView_language_name) as TextView
        val imageView_language=view.findViewById<ImageView>(R.id.imageView_language)


        textView_language_name.text= languageList.get(position).toString()
        Glide
            .with(context)
            .load("https://miro.medium.com/max/1000/1*ilC2Aqp5sZd1wi0CopD1Hw.png")
            .centerCrop()
            .placeholder(R.drawable.googleg_standard_color_18)
            .into(imageView_language);

        return view
    }

}