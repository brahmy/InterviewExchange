package com.asab.interviewexchange

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.asab.interviewexchange.databinding.FragmentChooseLangugeBinding
import com.google.firebase.database.*
import androidx.navigation.fragment.findNavController


class ChooseLanguageFragment : Fragment() {
    private var _binding: FragmentChooseLangugeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var database: DatabaseReference
    private var listLanguage: ArrayList<String>

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
        getLanguages()
        onClickViews()
    }

    private fun onClickViews() {
        binding.gridViewChooseLanguage.onItemClickListener =
            OnItemClickListener { parent, v, position, id ->

                var bundle: Bundle = Bundle()
                bundle.putString("lName", listLanguage.get(position))
                findNavController().navigate(R.id.action_LanguageFragment_to_FirstFragment, bundle)

            }
    }

    private fun getLanguages() {
        database.keepSynced(true)
        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    listLanguage.clear()
                    binding.idProgressBar.visibility=View.GONE
                    for (npsnapshot in snapshot.getChildren()) {
                        for (dSnapshot in npsnapshot.children) {
                            listLanguage.add(dSnapshot.getKey()!!)

                        }

                    }
                    //add adapter
                    binding.gridViewChooseLanguage.adapter =
                        AdapterChooseLanguage(listLanguage, requireActivity())

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