package com.asab.interviewexchange

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.asab.interviewexchange.databinding.FragmentTopicDetailsBinding
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.firebase.database.*

class TopicDetailsFragment:Fragment() {
    private var _binding: FragmentTopicDetailsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var database: DatabaseReference
    private var description:String?=null
    private var images:String?=null
    private var topic:String?=null
    private var topic_name:String?=null
    private var listImages:ArrayList<String>
    private var mAdView: AdView? = null



    init {
        listImages=ArrayList<String>()
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
        _binding = FragmentTopicDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bannerLoad()
        getBundledData()
        getTopicDetails()
        onClick()
    }

    private fun getBundledData() {
        var bundle:Bundle?=arguments
        topic_name=bundle?.getString("topic_name")
        binding?.idTextviewTopic.text=topic_name
        topic=bundle?.getString("topic")
    }
    private fun onClick(){


    }
    private fun bannerLoad() {
        mAdView = binding.adView
        val adRequest = AdRequest.Builder().build()
        mAdView!!.loadAd(adRequest)
    }


    private fun getTopicDetails() {
        database.keepSynced(true)
        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    binding.idProgressBar.visibility=View.GONE
                    listImages.clear()
                    for (npsnapshot in snapshot.children) {
                        if (npsnapshot.key == topic) {
                            for (psnapshot in npsnapshot.children) {
                                for (qsnapshot in psnapshot.children) {
                                    if (qsnapshot.key == topic_name) {
                                        for (lsnapshot in qsnapshot.children) {
                                            if (lsnapshot?.key == "description") {
                                            description = lsnapshot.value.toString()
                                        }
                                        if (lsnapshot?.key == "image") {
                                            val lstValues: List<String> =
                                                lsnapshot?.value.toString().split(",")
                                            lstValues.forEach { it ->
                                                listImages.add(it)
                                            }
                                        }
                                        }
                                    }
                                }
                            }

                        }

                    }

                    binding.idImageViewPager.adapter=TopicImageAdapter(requireContext(),listImages)

                    binding.idTextviewTopicDetailed.text=description?.replace("\\n","\n")
                }

            }

            override fun onCancelled(error: DatabaseError) {
                println("displayError:" + error.message)
            }

        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}