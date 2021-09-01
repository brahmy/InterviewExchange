package com.asab.interviewexchange

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
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
    private lateinit var adapter:QuestionsAdapter


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

//        Utils.hideSoftKeyBoard(requireActivity())
//        bannerLoad()
        widgetListeners()
        return binding.root

    }

    private fun widgetListeners() {
        binding.idSeachview.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                // inside on query text change method we are
                // calling a method to filter our recycler view.
                filter(newText)
                return false
            }
        })

    }
    private fun filter(text: String) {
        // creating a new array list to filter our data.
        val filteredlist: ArrayList<QuestionsDetails> = ArrayList()

        // running a for loop to compare elements.
        for (item in listQuestion) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.question?.toLowerCase()!!.contains(text.toLowerCase())) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredlist.add(item)
            }
        }
        if (filteredlist.isEmpty()) {
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.
//            Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show()
        } else {
            // at last we are passing that filtered
            // list to our adapter class.
            adapter .filterList(filteredlist)
        }
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

    override fun onPause() {
        super.onPause()
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

                    adapter = QuestionsAdapter(requireContext(), listQuestion)
                    binding.idRecycleView.adapter = adapter

                }
            }

            override fun onCancelled(error: DatabaseError) {
                println("displayError:" + error.message)
            }

        })

    }
}