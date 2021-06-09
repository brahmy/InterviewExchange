package com.asab.interviewexchange;

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.asab.interviewexchange.databinding.FragmentFirstBinding
import com.asab.interviewexchange.databinding.FragmentNotificationBinding
import com.google.firebase.database.*


class NotificationFragment: Fragment() {

    private var _binding: FragmentNotificationBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var database: DatabaseReference
    private var notificationDataModelList: ArrayList<NotificationDataModel>
    private var topic_name:String?=null
    private var topic:String?=null


    init {
        notificationDataModelList = ArrayList<NotificationDataModel>()
        initializeDB()

    }
    fun initializeDB() {
        database = FirebaseDatabase.getInstance().reference;
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNotificationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        getBundleData()
        getNotifications()
    }

    private fun getNotifications() {
        database.keepSynced(true)
        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    notificationDataModelList.clear()
                    binding.idProgressBar.visibility=View.GONE
                    for (npsnapshot in snapshot.getChildren()) {
                        if (npsnapshot.key == topic) {
                            for (dSnapshot in npsnapshot.children) {
                                if (dSnapshot.key == topic_name) {
                                    for (eSnapshot in dSnapshot.children) {
                                        val notificationDataModel: NotificationDataModel =
                                            eSnapshot.getValue(NotificationDataModel::class.java)!!
                                        notificationDataModel?.let {
                                            notificationDataModelList.add(
                                                it
                                            )
                                        }
                                    }
                                }
                            }
                        }

                    }
                    binding.idRecycleViewNotifications.adapter = AdapterNotifications(requireContext(), notificationDataModelList)

                }
            }

            override fun onCancelled(error: DatabaseError) {
                println("displayError:" + error.message)
            }

        })
    }

    private fun getBundleData() {
        var bundle: Bundle? = arguments
        topic_name = bundle?.getString("lName")
        topic = bundle?.getString("topic")


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
