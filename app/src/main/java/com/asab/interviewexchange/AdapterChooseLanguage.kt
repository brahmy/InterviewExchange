package com.asab.interviewexchange

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide

data class AdapterChooseLanguage(private var languageList:List<String>, private val context: Context) : BaseAdapter(){
    private var language_icon:Int?=null

    override fun getItem(position: Int): Any {
        return languageList.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return languageList.size
    }

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = View.inflate(context,R.layout.adapter_choose_language,null)

        val textView_language_name = view.findViewById<TextView>(R.id.textView_language_name) as TextView
        val imageView_language=view.findViewById<ImageView>(R.id.imageView_language)


        textView_language_name.text= languageList.get(position).toString()
        if(languageList.get(position)=="Flutter"){

            language_icon=R.drawable.ic_flutter
        }else if(languageList.get(position)=="Java" || languageList.get(position)=="JDBC"||languageList.get(position)=="JSP"||languageList.get(position)=="Servlet"){

            language_icon=R.drawable.ic_java_logo
        }else if(languageList.get(position)=="Hibernate"){

            language_icon=R.drawable.ic_hibernate
        }else if(languageList.get(position)=="Spring"){

            language_icon=R.drawable.ic_spring_logo
        }else if(languageList.get(position)=="spring boot"){

            language_icon=R.drawable.ic_spring_boot
        }else if(languageList.get(position)=="Kotlin"){

            language_icon=R.drawable.ic_kotlin
        }
        else{

            language_icon=R.drawable.ic_logo
        }
;
        Glide
            .with(context)
            .load(language_icon)
            .centerCrop()
            .placeholder(android.R.drawable.picture_frame)
            .into(imageView_language)


        return view
    }

}