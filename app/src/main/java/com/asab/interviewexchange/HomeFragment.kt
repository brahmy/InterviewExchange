package com.asab.interviewexchange

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.asab.interviewexchange.databinding.FragmentHomeBinding
import com.google.firebase.database.*

class HomeFragment:Fragment() {
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var database: DatabaseReference
    private var homeData: ArrayList<String>


    init {
        homeData = ArrayList<String>()
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
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getAppInformation()

    }

    private fun getAppInformation() {
        database.keepSynced(true)
        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    homeData.clear()
                    binding.idProgressBar.visibility=View.GONE
                    for (npsnapshot in snapshot.children) {
                        println("topicname2:"+npsnapshot.key)
                        npsnapshot.key?.let { homeData.add(it) }

                        }
                    println("topicname3:"+homeData[0])
                    binding.homeRecyclerView.adapter = AdapterHome(requireContext(), homeData)

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