package com.asab.interviewexchange

import android.app.Application
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)
    }
}