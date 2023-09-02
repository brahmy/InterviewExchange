package com.asab.interviewexchange

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.asab.interviewexchange.databinding.FragmentSecondBinding
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView

class AnswerDetailsPagerAdapter(
    private val questionsDetailsList: List<QuestionsDetails>) : RecyclerView.Adapter<AnswerDetailsPagerAdapter.DetailViewHolder>() {

    private var _binding: FragmentSecondBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    class DetailViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {
        
        fun bind(
            questionsDetails: QuestionsDetails
        ) {
            itemView.findViewById<TextView>(R.id.textview_question).text = questionsDetails.question
            itemView.findViewById<TextView>(R.id.textview_answer).text = questionsDetails.answer
            val adRequest = AdRequest.Builder().build()
            itemView.findViewById<AdView>(R.id.adView).loadAd(adRequest)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailViewHolder {
        _binding = FragmentSecondBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return DetailViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: DetailViewHolder, position: Int) {
        val sportsArgs: QuestionsDetails = questionsDetailsList[position]
        holder.bind(sportsArgs)

    }

    override fun getItemCount() = questionsDetailsList.size
}