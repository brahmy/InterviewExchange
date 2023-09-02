package com.asab.interviewexchange

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.asab.interviewexchange.databinding.FragmentAnswersDetailsBinding

class AnswerDetailsFragment: Fragment(){

    private var _binding: FragmentAnswersDetailsBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAnswersDetailsBinding.inflate(layoutInflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadBundleData()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun loadBundleData() {
        var bundle:Bundle?=arguments
        val serializableList = bundle?.getSerializable("answers")!!
        var list:List<QuestionsDetails> = serializableList as List<QuestionsDetails>

       /* var questionsDetails:QuestionsDetails = serializableList as QuestionsDetails
        var list:List<QuestionsDetails> = listOf()
        list = listOf(questionsDetails)
*/
        val viewPagerAdapter = AnswerDetailsPagerAdapter(list)
        _binding?.idDetailViewPager?.adapter = viewPagerAdapter
        _binding?.idDetailViewPager?.setPageTransformer(DepthPageTransformer())
        var position = bundle?.getInt("position")

        _binding?.idDetailViewPager?.setCurrentItem(position,false)

/*
        list.forEachIndexed { index, questionsDetails ->
            _binding?.idDetailViewPager?.setCurrentItem(index,false)
        }
*/
    }
}