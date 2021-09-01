package com.asab.interviewexchange

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.SearchView.OnQueryTextListener
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.asab.interviewexchange.databinding.FragmentChooseLangugeBinding
import com.google.firebase.database.*


class ChooseLanguageFragment : Fragment() {
    private var _binding: FragmentChooseLangugeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var database: DatabaseReference
    private var listLanguage: ArrayList<String>
    private var app_info:String?=null
    private lateinit var adapter:AdapterChooseLanguage

    init {
        listLanguage = ArrayList<String>()
        initializeDB()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChooseLangugeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getBundleData()
        getLanguages()
        onClickViews()
    }

    private fun getBundleData(){
        val bundle:Bundle?=arguments
        app_info=bundle?.getString("app_info")
        (activity as MainActivity?)?.setActionBarTitle("Choose "+app_info)

    }

    private fun onClickViews() {
        binding.gridViewChooseLanguage.onItemClickListener =
            OnItemClickListener { parent, v, position, id ->

                if(app_info=="Language") {
                    var bundle: Bundle = Bundle()
                    bundle.putString("lName", parent.adapter.getItem(position).toString())
                    findNavController().navigate(
                        R.id.FirstFragment,
                        bundle
                    )
                }else if(app_info=="Topic"){
                    var bundle: Bundle = Bundle()
                    bundle.putString("topic", "Topic")
                    bundle.putString("lName", listLanguage.get(position))
                    findNavController().navigate(
                        R.id.TopicListFragment,
                        bundle
                    )

                }else if(app_info=="Notifications"){
                    var bundle: Bundle = Bundle()
                    bundle.putString("topic", "Notifications")
                    bundle.putString("lName", listLanguage.get(position))
                    findNavController().navigate(
                        R.id.NotificationFragment,
                        bundle
                    )

                }

            }

        binding.idSeachview.setOnQueryTextListener(object : OnQueryTextListener {
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
        val filteredlist: ArrayList<String> = ArrayList()

        // running a for loop to compare elements.
        for (item in listLanguage) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.toLowerCase().contains(text.toLowerCase())) {
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

    private fun getLanguages() {
        database.keepSynced(true)
        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    listLanguage.clear()
                    binding.idProgressBar.visibility=View.GONE
                    for (npsnapshot in snapshot.children) {
                        //Language,Topic
                        if(npsnapshot.key==app_info)
                        for (dSnapshot in npsnapshot.children) {
                            listLanguage.add(dSnapshot.getKey()!!)

                        }

                    }
                    //add adapter
                    adapter= AdapterChooseLanguage(listLanguage, requireActivity())

                    binding.gridViewChooseLanguage.adapter =adapter

                }

            }

            override fun onCancelled(error: DatabaseError) {
                println("LanguagesError:" + error.message)
            }

        })
    }

    fun initializeDB() {
        database = FirebaseDatabase.getInstance().getReference("Language").root;
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}