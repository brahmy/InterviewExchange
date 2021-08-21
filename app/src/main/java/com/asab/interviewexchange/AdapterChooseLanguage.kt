package com.asab.interviewexchange

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

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

            language_icon=R.drawable.ic_flutter_new
        }else if(languageList.get(position)=="Java" ||languageList.get(position)=="Java8" || languageList.get(position)=="JDBC"||languageList.get(position)=="JSP"||languageList.get(position)=="Servlet"){

            language_icon=R.drawable.ic_java_new
        }else if(languageList.get(position)=="Hibernate"){

            language_icon=R.drawable.ic_hibernate
        }else if(languageList.get(position)=="Spring"){

            language_icon=R.drawable.ic_spring_
        }else if(languageList.get(position).equals("Spring Boot",ignoreCase = true)|| languageList.get(position)=="Micro Services"){

            language_icon=R.drawable.ic_spring_boot_
        }else if(languageList.get(position)=="Kotlin"){

            language_icon=R.drawable.ic_kotlin
        }else if(languageList.get(position).equals("Dot Net",ignoreCase = true)){

            language_icon=R.drawable.ic_dot_net
        }else if(languageList.get(position).equals("HTML CSS JS",ignoreCase = true)){

            language_icon=R.drawable.ic_html
        }else if(languageList.get(position)=="Android"){

            language_icon=R.drawable.ic_nadroid
        }else if(languageList.get(position)=="Interview Programs"){

            language_icon=R.drawable.ic_code_
        }else if(languageList.get(position).equals("MongoDB",ignoreCase = true)){

            language_icon=R.drawable.ic_db
        }else if(languageList.get(position)=="Python"){

            language_icon=R.drawable.ic_python
        }else if(languageList.get(position)=="Oracle DBA"|| languageList.get(position).equals("SQL",ignoreCase = true)){

            language_icon=R.drawable.ic_oracle
        }else if(languageList.get(position)=="Junit"){

            language_icon=R.drawable.ic_junit
        }else if(languageList.get(position)=="PHP"){

            language_icon=R.drawable.ic_php
        }else if(languageList.get(position)=="Testing"){

            language_icon=R.drawable.ic_testing
        }else if(languageList.get(position)=="DevOps"){

            language_icon=R.drawable.ic_dev_ops
        }else if(languageList.get(position)=="AWS"){

            language_icon=R.drawable.ic_aws
        }else if(languageList.get(position)=="React Native" || languageList.get(position).equals("React JS",ignoreCase = true) ){

            language_icon=R.drawable.ic_react_native
        }
        else{

            language_icon=R.drawable.ic_logo_
        }
        ;
        Glide
            .with(context)
            .load(language_icon).centerCrop().centerInside()
            .apply(RequestOptions().override(150,150))
            .fitCenter()
            .placeholder(android.R.drawable.picture_frame)
            .into(imageView_language)


        return view
    }

    // method for filtering our recyclerview items.
    fun filterList(filterllist: List<String>) {
        // below line is to add our filtered
        // list in our course array list.
        languageList = filterllist
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged()
    }

}