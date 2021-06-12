package com.asab.interviewexchange

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.asab.interviewexchange.databinding.FragmentChooseLangugeBinding
import com.asab.interviewexchange.databinding.FragmentTopicListBinding
import com.google.firebase.database.*

class TopicListFragment:Fragment() {
    private var _binding:FragmentTopicListBinding?=null
    private val binding get() = _binding!!
    private lateinit var database: DatabaseReference
    private var topicData: ArrayList<String>
    private var topic_name:String?=null
    private var l_name:String?=null



    init {
        topicData = ArrayList<String>()
        initializeDB()
    }

    private fun initializeDB() {
        database = FirebaseDatabase.getInstance().reference;
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTopicListBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getBundleData()
        getAppInformation()


    }

    private fun getBundleData() {
       val bundle:Bundle?= arguments
        topic_name=bundle?.getString("topic")
        l_name=bundle?.getString("lName")

    }

    private fun getAppInformation() {
        database.keepSynced(true)
        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    topicData.clear()
                    binding.idProgressBar.visibility=View.GONE
                    for (npsnapshot in snapshot.children) {
                        if (npsnapshot.key == topic_name) {
                            for (psnapshot in npsnapshot.children) {
                                if (psnapshot.key == l_name) {
                                    for (qsnapshot in psnapshot.children) {
                                        qsnapshot.key?.let {
                                            topicData.add(it)
                                        }
                                    }
                                }

                            }
                        }

                    }
                    binding.topicsRecyclerView.adapter = AdapterTopicList(requireContext(), topicData)

                }

            }

            override fun onCancelled(error: DatabaseError) {
                println("displayError:" + error.message)
            }

        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
}