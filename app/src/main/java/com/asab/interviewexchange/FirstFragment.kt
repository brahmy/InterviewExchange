package com.asab.interviewexchange

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.asab.interviewexchange.databinding.FragmentFirstBinding
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.firebase.database.*


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var database: DatabaseReference
    private var listQuestion: ArrayList<QuestionsDetails>
    private var lName: String? = null
    private var mAdView: AdView? = null


    init {
        listQuestion = ArrayList<QuestionsDetails>()
        initializeDB()
    }

    private fun getBundleData() {
        var bundle: Bundle? = arguments
        lName = bundle?.getString("lName")
        (activity as MainActivity?)?.setActionBarTitle(lName)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)


//        bannerLoad()
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getBundleData()
        getQuestions()
    }

    private fun bannerLoad() {
        mAdView = binding.adView
        val adRequest = AdRequest.Builder().build()
        mAdView!!.loadAd(adRequest)
    }

    fun initializeDB() {
        database = FirebaseDatabase.getInstance().getReference("Language");
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun getQuestions() {
        database.keepSynced(true)
        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    listQuestion.clear()
                    binding.idProgressBar.visibility=View.GONE
                    for (npsnapshot in snapshot.getChildren()) {
                        if (npsnapshot.key == lName) {
                            for (dSnapshot in npsnapshot.children) {
                                val questionsDetails: QuestionsDetails =
                                    dSnapshot.getValue(QuestionsDetails::class.java)!!
                                questionsDetails?.let { listQuestion.add(it) }
                        }
                        }

                    }

                    binding.idRecycleView.adapter = QuestionsAdapter(requireContext(), listQuestion)

                }
            }

            override fun onCancelled(error: DatabaseError) {
                println("displayError:" + error.message)
            }

        })

    }

}