package com.asab.interviewexchange

import android.content.Context
import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.navigation.fragment.findNavController


class QuestionsAdapter(private val context:Context,private var listQuestions:List<QuestionsDetails>):RecyclerView.Adapter<QuestionsAdapter.MyViewHolder>(){


    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView_question: TextView
        val textView_answer: TextView
        val textView_more: TextView


        init {
            // Define click listener for the ViewHolder's View.
            textView_question = view.findViewById(R.id.textview_question)
            textView_answer = view.findViewById(R.id.textview_answer)
            textView_more=view.findViewById(R.id.textview_more)
            textView_more.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG)

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_questions_list, parent, false)

        return MyViewHolder(view).listen { position, type ->
            var bundle:Bundle= Bundle()
            bundle.putString("Question",listQuestions.get(position).question)
            bundle.putString("Answer",listQuestions.get(position).answer)
            bundle.putString("Image_code",listQuestions.get(position).image)
            Navigation.findNavController(view).navigate(R.id.SecondFragment,bundle);
        }
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        holder.textView_question.text = listQuestions.get(position).question
        holder.textView_answer.text = listQuestions.get(position).answer?.replace("\\n","")

    }

    override fun getItemCount(): Int {
        return listQuestions.size
    }
    fun <T : RecyclerView.ViewHolder> T.listen(event: (position: Int, type: Int) -> Unit): T {
        itemView.setOnClickListener {
            event.invoke(bindingAdapterPosition, itemViewType)
        }
        return this
    }
    // method for filtering our recyclerview items.
    fun filterList(filterllist: List<QuestionsDetails>) {
        // below line is to add our filtered
        // list in our course array list.
        listQuestions = filterllist
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged()
    }

}